----------------------------------------------------------------------
-- Fichero: RegsMIPS.vhd
-- Descripci�n: Banco de registros de prop�sito general
-- Fecha �ltima modificaci�n: 2-3-2013

-- Autores: Roberto Garc�a Teodoro y Luis Cayola P�rez
-- Asignatura: E.C. 1� grado
-- Grupo de Pr�cticas: 2111
-- Grupo de Teor�a: 211
-- Pr�ctica: 3
-- Ejercicio: 2
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use IEEE.std_logic_arith.all;

entity RegsMIPS is
		port (
		Clk : in std_logic; -- Reloj
		Reset : in std_logic; -- Reset as�ncrono a nivel bajo
		A1 : in std_logic_vector(4 downto 0); -- Direcci�n para el puerto Rd1
		Rd1 : out std_logic_vector(31 downto 0); -- Dato del puerto Rd1
		A2 : in std_logic_vector(4 downto 0); -- Direcci�n para el puerto Rd2
		Rd2 : out std_logic_vector(31 downto 0); -- Dato del puerto Rd2
		A3 : in std_logic_vector(4 downto 0); -- Direcci�n para el puerto Wd3
		Wd3 : in std_logic_vector(31 downto 0); -- Dato de entrada Wd3
		We3 : in std_logic -- Habilitaci�n del banco de registros
	);
end RegsMIPS;

architecture Practica of RegsMIPS is

	-- Tipo para almacenar los registros
	type regs_t is array (0 to 31) of std_logic_vector(31 downto 0);

	-- Esta es la se�al que contiene los registros. El acceso es de la
	-- siguiente manera: regs(i) acceso al registro i, donde i es
	-- un entero. Para convertir del tipo std_logic_vector a entero se
	-- hace de la siguiente manera: conv_integer(slv), donde
	-- slv es un elemento de tipo std_logic_vector

	-- Registros inicializados a '0' 
	signal regs : regs_t;

begin  -- PRACTICA

	------------------------------------------------------
	-- Escritura del registro RT
	------------------------------------------------------
	
	process (Clk, Reset)
	begin
	if (Reset = '0') then
		for i in 0 to 31 loop
			regs(i)<= x"00000000";
			end loop;
	
	elsif falling_edge(Clk) then
	if We3='1' then --Si en enable est� activado, puede proceder a escribir.
   if A3/="00000" then --Se comprueba adem�s que el registro en el que escribir sea diferente al 0.
		regs(conv_integer(A3))<=Wd3;
		end if;
		end if;
		end if;
		
		end process;


	------------------------------------------------------
	-- Lectura del registro RS
	------------------------------------------------------

	Rd1 <= regs(conv_integer(A1));
	Rd2 <= regs(conv_integer(A2));





end Practica;


