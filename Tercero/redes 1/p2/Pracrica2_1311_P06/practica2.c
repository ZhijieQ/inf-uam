/***************************************************************************
 practica2.c
 Muestra las direciones Ethernet de la traza que se pasa como primer parametro.
 Debe complatarse con más campos de niveles 2, 3, y 4 tal como se pida en el enunciado.
 Debe tener capacidad de descartar paquetes de acuerdo a un filtro.
 
 Compila: gcc -Wall -o practica2 practica2.c -lpcap, make
 Autor: Jose Luis Garcia Dorado
 2014 EPS-UAM 
***************************************************************************/

#include <stdio.h>
#include <stdlib.h>

#include <pcap.h>
#include <string.h>
#include <netinet/in.h>
#include <linux/udp.h>
#include <linux/tcp.h>
#include <signal.h>
#include <time.h>
#include <getopt.h>
#include <inttypes.h>

/*Definicion de constantes ***********************************************/
#define ETH_ALEN      6      /* Tamano de direccion ethernet             */
#define ETH_HLEN      14     /* Tamano de cabecera ethernet              */
#define ETH_TLEN      2      /* Tamano del campo tipo ethernet           */
#define ETH_FRAME_MAX 1514   /* Tamano maximo trama ethernet (sin CRC)   */
#define ETH_FRAME_MIN 60     /* Tamano minimo trama ethernet (sin CRC)   */
#define ETH_DATA_MAX  (ETH_FRAME_MAX - ETH_HLEN) /* Tamano maximo y minimo de los datos de una trama ethernet*/
#define ETH_DATA_MIN  (ETH_FRAME_MIN - ETH_HLEN)
#define IP_ALEN 4			/* Tamano de direccion IP					*/
#define OK 0
#define ERROR 1

uint8_t analizarPaquete(uint8_t*,struct pcap_pkthdr*,uint64_t);
void handleSignal(int nsignal);

pcap_t* descr;
uint64_t contador=0;
uint8_t ipo_filtro[IP_ALEN]={0};
uint8_t ipd_filtro[IP_ALEN]={0};
uint16_t po_filtro=0;
uint16_t pd_filtro=0;

void handleSignal(int nsignal){
	printf("Control C pulsado (%llu)\n", contador);
	pcap_close(descr);
	exit(OK);
}

int main(int argc, char **argv){
	int contador2=0;
	char errbuf[PCAP_ERRBUF_SIZE];
	char entrada[256];
	uint8_t *paquete;
	struct pcap_pkthdr *cabecera;
	int long_index=0,retorno=0;
	char opt;
	int flag=0;
	if(signal(SIGINT,handleSignal)==SIG_ERR){
		printf("Error: Fallo al capturar la senal SIGINT.\n");
		exit(ERROR);
	}
	
	if(argc<=1) {
		printf("Ejecucion: %s /ruta/captura_pcap <-t traza/interfaz de red> <-ipo IPO> <-ipd IPD> <-po PO> <-pd PD>: %d\n",argv[0],argc);
		exit(ERROR);
	}
	printf("lee las intrucciones\n");
	static struct option options[] = {
		{"ipo",required_argument,0,'1'},
		{"ipd",required_argument,0,'2'},
		{"po",required_argument,0,'3'},
		{"pd",required_argument,0,'4'},
		{"h",no_argument,0,'5'},
		{"t",required_argument,0,'6'},
		{0,0,0,0,0}
	};
		//Simple lectura por parametros por completar casos de error, ojo no cumple 100% los requisitos del enunciado!
	while ((opt = getopt_long_only(argc, argv,"1:2:3:4:5 6:", options, &long_index )) != -1) {
		switch (opt) {
			case '1' : 
				if(sscanf(optarg,"%hhu.%"SCNu8".%"SCNu8".%"SCNu8"",&(ipo_filtro[0]),&(ipo_filtro[1]),&(ipo_filtro[2]),&(ipo_filtro[3]))!=IP_ALEN){
					printf("Error ipo_filtro. Ejecucion: %s /ruta/captura_pcap <-t traza/interfaz de red> <-ipo IPO> <-ipd IPD> <-po PO> <-pd PD>: %d\n",argv[0],argc); exit(ERROR);
				}
				break;
			case '2' : 
				if(sscanf(optarg,"%"SCNu8".%"SCNu8".%"SCNu8".%"SCNu8"",&(ipd_filtro[0]),&(ipd_filtro[1]),&(ipd_filtro[2]),&(ipd_filtro[3]))!=IP_ALEN){
					printf("Error ipd_filtro. Ejecucion: %s /ruta/captura_pcap <-t traza/interfaz de red> <-ipo IPO> <-ipd IPD> <-po PO> <-pd PD>: %d\n",argv[0],argc); exit(ERROR);
				}
				break;
			case '3' : if((po_filtro = atoi(optarg))==0){
							printf("Error o_filtro.Ejecucion: %s /ruta/captura_pcap <-t traza/interfaz de red> <-ipo IPO> <-ipd IPD> <-po PO> <-pd PD>: %d\n",argv[0],argc); exit(ERROR);
						}					
				break;
			case '4' : if((pd_filtro = atoi(optarg))==0){
							printf("Error pd_filtro. Ejecucion: %s /ruta/captura_pcap <-t traza/interfaz de red> <-ipo IPO> <-ipd IPD> <-po PO> <-pd PD>: %d\n",argv[0],argc); exit(ERROR);
						}	
				break;
			case '5' : printf("Ayuda. Ejecucion: %s /ruta/captura_pcap <-t traza/interfaz de red> <-ipo IPO> <-ipd IPD> <-po PO> <-pd PD>: %d\n",argv[0],argc); exit(ERROR);
				break;
			case '6': printf("caso 6\n");
					if(sscanf(optarg,"%s",entrada)){
						if((strncmp(entrada,"eth",3)==0)||(strncmp(entrada,"wlan",4))==0){
							printf("Lectura desde la interfaz de red %s\n",entrada);
							sleep(1);
							flag=1;
						}
					
					}
				
				break;
			case '?' : printf("Error.Ejecucion: %s /ruta/captura_pcap <-t traza/interfaz de red> <-ipo IPO> <-ipd IPD> <-po PO> <-pd PD>: %d\n",argv[0],argc); exit(ERROR);
				break;
			default: printf("Error.Ejecucion: %s /ruta/captura_pcap <-t traza/interfaz de red> <-ipo IPO> <-ipd IPD> <-po PO> <-pd PD>: %d\n",argv[0],argc); exit(ERROR);
				break;
        }
    }
		//Simple comprobacion de la correcion de la lectura de parametros
	printf("Entrada: %s\n",entrada);	
	printf("Filtro:");
	//if(ipo_filtro[0]!=0)
		printf("ipo_filtro:%hhu.%hhu.%hhu.%hhu\t",ipo_filtro[0],ipo_filtro[1],ipo_filtro[2],ipo_filtro[3]);
	//if(ipd_filtro[0]!=0)
		printf("ipd_filtro:%"SCNu8".%"SCNu8".%"SCNu8".%"SCNu8"\t",ipd_filtro[0],ipd_filtro[1],ipd_filtro[2],ipd_filtro[3]);
	if(po_filtro!=0)
		printf("po_filtro=%hu\t",po_filtro);
	if(pd_filtro!=0)
		printf("pd_filtro=%"SCNu16,pd_filtro);
	printf("\n\n");
	
	if(flag==1){
		if ((descr = pcap_open_live(entrada,256,0,100, errbuf)) == NULL){
			printf("Error: pcap_open_live(): %s, %s %d.\n",errbuf,__FILE__,__LINE__);
			exit(ERROR);
		}
	}
	else{
	   	if ( (descr = pcap_open_offline(entrada, errbuf)) == NULL){
	   		printf("Error: pcap_open_offline(): File: %s, %s %s %d.\n", argv[1],errbuf,__FILE__,__LINE__);
			exit(ERROR);
		}
	}
	while((retorno = pcap_next_ex(descr,&cabecera,(const u_char **)&paquete)) >=0){
		if(retorno == 0){
			continue;
		}
		contador++;
		contador2++;
		printf("Numero de paquete= %d\n", contador2);
		if( (retorno=analizarPaquete(paquete,cabecera,contador)) ==ERROR){
			printf("Error al analizar el paquete %llu; %s %d.\n",contador,__FILE__, __LINE__);
			exit(ERROR);
		}
	}
	if(retorno==-1){
		printf("Error al capturar un paquete %s, %s %d.\n",pcap_geterr(descr),__FILE__,__LINE__);
		pcap_close(descr);
		exit(ERROR);
	}
	printf("No hay mas paquetes (%llu).\n\n",contador);
	pcap_close(descr);
	return OK;
}


uint8_t analizarPaquete(uint8_t* paquete,struct pcap_pkthdr* cabecera,uint64_t contador){
	uint8_t auxByte, protocolo=0;
	int i=0,aux, acumulador=0,tamIP=0;
	
	printf("NIVEL 2\n");
	printf("\tDireccion ETH destino= ");	
	printf("%02X",paquete[0]);
	for (i=1;i<ETH_ALEN;i++){
		printf(":%02X",paquete[i]);
	}
	printf("\n");
	paquete+=ETH_ALEN;	

	printf("\tDireccion ETH origen = ");	
	printf("%02X",paquete[0]);
	for (i=1;i<ETH_ALEN;i++){
		printf(":%02X",paquete[i]);
	}

	printf("\n");
	paquete+=ETH_ALEN;
	printf("\tTipo de Ethernet     = ");	
	printf("%02X",paquete[0]);
	for (i=1;i<ETH_TLEN;i++){
		printf(":%02X",paquete[i]);
	}
	
	if((0x08==paquete[12])&&(0x00==paquete[13])){
		printf("\n\nNIVEL 3\n");
		paquete+=ETH_TLEN;
		auxByte=paquete[0];
		auxByte >>= 4;
		printf("\tTipo de IP            = ");	
		printf("%d",auxByte);
		printf("\n");

		auxByte=paquete[0];
		auxByte &= 15;
		tamIP=auxByte;
		tamIP*=4;
		printf("\tLonguitud de cabecera = ");	
		printf("%d",auxByte);
		printf("\n");

		paquete+=2;
		acumulador+=2;
		aux=paquete[0];
		aux<<=8;
		aux+=paquete[1];
		printf("\tLonguitud Total       = %d \n",aux);

		paquete+=4;
		acumulador+=4;
		aux=paquete[0];
		aux &= 0x0f;
		aux <<=8;
		aux+=paquete[1];
		aux*=8;
		printf("\tPosicion/Desplaz.     = %d\n", aux);


		paquete+=2;
		acumulador+=2;
		printf("\tTiempo de vida        = ");
		printf("%d\n",paquete[0]);
		printf("\tProtocolo             = ");
		printf("%d\n",paquete[1]);
		protocolo=paquete[1];

		paquete+=4;
		acumulador+=4;
		if ((ipo_filtro[0]==paquete[0])&&(ipo_filtro[1]==paquete[1])&&(ipo_filtro[2]==paquete[2])&&(ipo_filtro[3]==paquete[3])||
			(ipo_filtro[0]==0)&&(ipo_filtro[1]==0)&&(ipo_filtro[2]==0)&&(ipo_filtro[3]==0)){
			printf("\tDireccion de origen   = ");
			printf("%d.%d.%d.%d\n",paquete[0],paquete[1],paquete[2],paquete[3]);
		}
		else{
			printf("La ip origen no es la del filtro\n");
		return;
		}
		paquete+=4;
		acumulador+=4;
		if ((ipd_filtro[0]==paquete[0])&&(ipd_filtro[1]==paquete[1])&&(ipd_filtro[2]==paquete[2])&&(ipd_filtro[3]==paquete[3])||
				(ipd_filtro[0]==0)&&(ipd_filtro[1]==0)&&(ipd_filtro[2]==0)&&(ipd_filtro[3]==0)){
			printf("\tDireccion de destino  = ");
			printf("%d.%d.%d.%d\n",paquete[0],paquete[1],paquete[2],paquete[3]);
		}
		else{
			printf("La ip destino no es la del filtro\n");
		return;
		}

	}
	
	switch(protocolo){
		case 6:	//TCP
			printf("\nNIVEL 4, PROTOCOLO TCP\n");
			paquete+=tamIP-acumulador;
			aux=paquete[0];
			aux<<=8;
			aux+=paquete[1];
			if((po_filtro==0)||(po_filtro==aux)){
				printf("\tPuerto Origen  =%d\n",aux);
			}
			else{
				printf("El puerto origen no coincide con el especifacado en el filtro\n");
				return;
			}
			paquete+=2;
			aux=paquete[0];
			aux<<=8;
			aux+=paquete[1];
			if((pd_filtro==0)||(pd_filtro==aux)){
				printf("\tPuerto Destino =%d\n",aux);
			}
			else{
				printf("El puerto destino no coincide con el especifacado en el filtro\n");
				return;
			}
		break;
		case 17:	//UDP
			printf("\nNIVEL 4, PROTOCOLO UDP\n");
			paquete+=tamIP-acumulador;
			aux=paquete[0];
			aux<<=8;
			aux+=paquete[1];

			if((po_filtro==0)||(po_filtro==aux)){
				printf("\tPuerto Origen  =%d\n",aux);
			}
			else{
				printf("El puerto origen no coincide con el especifacado en el filtro\n");
			}

			paquete+=4;
			aux=paquete[0];
			aux<<=8;
			aux+=paquete[1];
			if((pd_filtro==0)||(pd_filtro==aux)){
				printf("\tPuerto Destino =%d\n",aux);
			}
			else{
				printf("El puerto destino no coincide con el especifacado en el filtro\n");
			}
			paquete+=4;
			aux=paquete[0];
			aux<<=8;
			aux+=paquete[1];
			printf("\tLonguitud      =%d\n",aux);
		break;
		default:
		break;
	}	
	printf("\n");
	printf("\n\n");
	return OK;
}
