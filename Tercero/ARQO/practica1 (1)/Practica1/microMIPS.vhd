----------------------------------------------------------------------
-- Fichero: MicroMIPS.vhd
-- Descripci�n: Microprocesador MIPS
-- Fecha �ltima modificaci�n: 2012-04-15

-- Autores: Luis Cayola P�rez y Roberto Garc�a Teodoro
-- Asignatura: E.C. 1� grado
-- Grupo de Pr�cticas: 2111
-- Grupo de Teor�a: 211
-- Pr�ctica: 5
-- Ejercicio: 3
----------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;

entity microMIPS is
port(
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Reset activo a nivel bajo
		MemProgAddr : out std_logic_vector(31 downto 0); -- Direcci�n para la memoria de programa
		MemProgData : in std_logic_vector(31 downto 0); -- C�digo de operaci�n
		MemDataAddr : out std_logic_vector(31 downto 0); -- Direcci�n para la memoria de datos
		MemDataDataRead : in std_logic_vector(31 downto 0); -- Dato a leer en la memoria de datos
		MemDataDataWrite : out std_logic_vector(31 downto 0); -- Dato a guardar en la memoria de datos
		MemDataWE : out std_logic
	);
end microMIPS;



architecture MIPS of microMIPS is


signal auxPc,auxRd1,auxRd2,auxWd3,auxMuxAluSrc,auxMuxJump,auxRes,auxMuxMemToReg,auxMuxPcSrc,BeqAddr1,BeqAddr2,sigExtSigno,sigExtCero,JumpAddr,auxPC4: std_logic_vector(31 downto 0);
signal auxEq, auxWe3,auxMemToReg,auxBranch,auxAluSrc,auxRegDest,auxJump,auxEqInv,auxPcSrc: std_logic;
signal auxAluControl :std_logic_vector(2 downto 0);
signal auxA3,auxMuxRegDest: std_logic_vector(4 downto 0);


	
	
component ALUMIPS --definimos la ALU
	port (
		Op1: in std_logic_vector(31 downto 0); -- Operando
		Op2: in std_logic_vector(31 downto 0); -- Operando
		ALUControl: in std_logic_vector(2 downto 0); -- Operacion
		Res: out std_logic_vector(31 downto 0); -- Resultado
		Z: out std_logic -- Bandera
	);
end component;




component UnidadControlMIPS
	port (
		OPCode : in std_logic_vector(31 downto 26);
		Funct : in std_logic_vector(5 downto 0);
		MemToReg : out std_logic;
		MemWrite : out std_logic;
		Branch : out std_logic;
		ALUControlUC : out std_logic_vector(2 downto 0);
		ALUSrc : out std_logic;
		RegDest : out std_logic;
		RegWrite : out std_logic;
		--RegToPc : out std_logic;
		--ExtCero : out std_logic;
		Jump : out std_logic
		--PCToReg : out std_logic
	);
	end component;
	



component RegsMIPS
		port (
		Clk : in std_logic; -- Reloj
		NRst : in std_logic; -- Reset as�ncrono a nivel bajo
		A1 : in std_logic_vector(4 downto 0); -- Direcci�n para el puerto Rd1
		Rd1 : out std_logic_vector(31 downto 0); -- Dato del puerto Rd1
		A2 : in std_logic_vector(4 downto 0); -- Direcci�n para el puerto Rd2
		Rd2 : out std_logic_vector(31 downto 0); -- Dato del puerto Rd2
		A3 : in std_logic_vector(4 downto 0); -- Direcci�n para el puerto Wd3
		Wd3 : in std_logic_vector(31 downto 0); -- Dato de entrada Wd3
		We3 : in std_logic -- Habilitaci�n del banco de registros
	);
	end component;


begin



--Registro contador de programa (PC). Se incrementa en 4 cada ciclo de reloj. El Reset as�ncrono.
process (Clk,NRst)
	begin 
		if NRst = '0' then 
			auxPc<=(others =>'0');
		elsif rising_edge (Clk) then 
			auxPc<= auxMuxJump;
		end if;
end process;
	
	MemProgAddr <= auxPc;
	
--Instanciaci�n del banco de registros.
miregs: RegsMIPS
port map
(		Clk =>Clk,
		NRst =>NRst,
		A1 => MemProgData(25 downto 21),--VOY A LEER UNA ENTRADA
		Rd1 => auxRd1, -- Dato del puerto Rd1
		A2 => MemProgData(20 downto 16), -- Direcci�n para el puerto Rd2
		Rd2 => auxRd2, -- Dato del puerto Rd2
		A3 => auxA3, -- Direcci�n para el puerto Wd3
		Wd3 =>auxWd3, -- Dato de entrada Wd3
		We3 => auxWe3 -- Habilitaci�n del banco de registros
		
	--LUEGO CUANDO INSTANCIE LA UC, DIRE QUE WE3 VAYA CON AUXWE3 (TENER EN CUENTA CUAL ES SALIDA Y CUAL ENTRADA)
		);


--Instanciaci�n de la ALU.
miALU: ALUMIPS
port map
(		Op1 => auxRd1 , -- Operando
		Op2 => auxMuxAluSrc, -- Operando --ESTE TIENE RELACION CON EL ALUSRC
		ALUControl => auxAluControl, -- Operacion
		Res => auxRes -- Resultado. Ser� lo que le entre a MemdataAddr
		--Z => auxZ -- Bandera
);

--Instanciaci�n de la unidad de Control.
miUC: UnidadControlMIPS
port map
(		OPCode=>MemProgData(31 downto 26),
		Funct =>MemProgData(5 downto 0),
		MemToReg => auxMemToReg,
		MemWrite => MemDataWe, --DUDA: MEMWRITE ES SALIDA PERO VA A MEMDATAWE,que tambien es salida???
		Branch =>auxBranch,
		ALUControlUC =>auxAluControl,
		ALUSrc=>auxAluSrc,
		RegDest =>auxRegDest,
		RegWrite =>auxWe3,
		--RegToPc=>auxRegToPc,
		--ExtCero =>auxExtCero,
		Jump =>auxJump
		--PCToReg =>auxPcToReg
);


auxPC4 <= auxPC+4;

MemDataDataWrite <= auxRd2;

MemDataAddr<=auxRes; --Asignamos a MemDataAddr la auxiliar del resultado de la ALU

--Extensi�n de ceros
--sigExtCero <=x"0000" & MemProgData(15 downto 0);

--Extensi�n de Signo (Si el msb es 1, extiende a 1.Si es 0, extiende a 0)
sigExtSigno<= x"0000" & MemProgData(15 downto 0) when MemProgdata(15)='0' else x"FFFF" & MemProgData(15 downto 0);


--Direcci�n para el salto condicional de la instrucci�n beq
BeqAddr1<= sigExtSigno(29 downto 0) & "00";
BeqAddr2<= BeqAddr1 + auxPC4;

--Direcci�n para jump
JumpAddr<= auxPC4(31 downto 28)& MemProgData(25 downto 0) & "00";


--Mux de ALUSrc
auxMuxAluSrc<=auxRd2 when auxAluSrc = '0' else sigExtSigno;

--Mux de RegToPc
--auxMuxRegToPC <= auxMuxJump when auxRegToPc = '0' else auxRd1;

--Mux de MemToReg
auxWd3 <= auxRes when auxMemToReg = '0' else MemDataDataRead;

--Mux de PCToReg
--auxA3<= auxMuxRegDest when auxPCToReg = '0' else "11111";
--auxWd3<=auxMuxMemToReg when auxPcToReg = '0' else auxPC4;

--Mux de RegDest
auxA3<= MemProgData(20 downto 16) when auxRegDest='0' else MemProgData(15 downto 11);

--Puerta AND para el PCSrc 
auxPcSrc<= auxEqInv and auxBranch;
--Inversion de auxEq
auxEqInv<= (not auxEq);--inversion

--Mux de PcSrc
auxMuxPcSrc<= auxPC4 when auxPcSrc='0' else BeqAddr2;

--Mux de ExtCero
--auxMuxExtcero <= sigExtSigno when auxExtCero='0' else sigExtCero;

--Mux de Jump
auxMuxJump <=  auxMuxPcSrc when auxJump ='0' else JumpAddr;

--Se�al AuxEq
auxEq <= '1' when auxRd1 = auxRd2 else '0';


end MIPS;