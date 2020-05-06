----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:25:33 06/17/2018 
-- Design Name: 
-- Module Name:    Count - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_unsigned.ALL;	

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Count is
    Port ( clk : in  STD_LOGIC;
			  XCounter : in  STD_LOGIC_VECTOR (9 downto 0);
           YCounter : in  STD_LOGIC_VECTOR (9 downto 0);
           VedioOn : in  STD_LOGIC;
			  Address_in : in   STD_LOGIC_VECTOR(18 downto 0);
           Address_out : out   STD_LOGIC_VECTOR(18 downto 0));
end Count;


architecture Behavioral of Count is

begin

process(clk)
begin 

	if(clk = '1' and clk'event) then
		
	
		if(XCounter= "000000001" and YCounter= "000000001") then
			
			Address_out <="0000000000000000001";
		
		elsif(VedioOn= '1') then 
		
			Address_out <= Address_in + 1;
			
		end if;
	
	end if;
		
	end process;

end Behavioral;

