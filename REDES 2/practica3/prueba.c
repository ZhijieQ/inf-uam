#include <stdio.h>
#include <string.h>

#include <stdlib.h>

int main(){

	char* i;
	i=malloc(5);
	int j;
	printf("%lu\n",sizeof(i));
	for(j=0;&i[j]!=NULL;j++);
	printf("%d\n", j);
}
