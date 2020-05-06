
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;


entity SyncGenarator is
    Port ( clk : in  STD_LOGIC;
				reset : in  STD_LOGIC;
           Hsync : out  STD_LOGIC;
           Vsync : out  STD_LOGIC;
           xCounter : out  STD_LOGIC_VECTOR (9 downto 0);
           yCounter : out  STD_LOGIC_VECTOR (9 downto 0);
           VideoOn : out  STD_LOGIC);
end SyncGenarator;

architecture Behavioral of SyncGenarator is


constant HD: integer:=640; 
constant HF: integer:=16 ; 
constant HB: integer:=48 ; 
constant HR: integer:=96 ;  
constant VD: integer:=480; 
constant VF: integer:=10 ; 
constant VB: integer:=33 ; 
constant VR: integer:=2  ; 

signal mod_reg, mod_next : unsigned(1 downto 0);

signal v_count_reg, v_count_next : unsigned(9 downto 0);
signal h_count_reg, h_count_next : unsigned(9 downto 0);


signal v_sync_reg, h_sync_reg: std_logic;
signal v_sync_next ,h_sync_next : std_logic;

signal h_end , v_end , pixel_tick: std_logic;
signal h_sync_d1_r ,h_sync_d2_r,v_sync_d1_r ,v_sync_d2_r: std_logic;
signal h_sync_d1_n ,h_sync_d2_n,v_sync_d1_n ,v_sync_d2_n: std_logic;

begin
   
    process(clk,reset)
        begin
            if (reset='1') then
                mod_reg     <=to_unsigned(0,2);
                v_count_reg  <=(others=>'0');
                h_count_reg  <=(others=>'0');
                v_sync_reg   <='0';
                h_sync_reg   <='0';
					 h_sync_d1_r <='0';
					 h_sync_d2_r <= '0';
					 v_sync_d1_r <= '0';
					 v_sync_d2_r <= '0';
					 
            elsif(clk'event and clk='1')then
                mod_reg     <=mod_next;
                v_count_reg  <=v_count_next;
                h_count_reg  <=h_count_next;
                v_sync_reg   <=v_sync_next;
                h_sync_reg   <=h_sync_next;
					 
					 v_sync_d1_r <= v_sync_d1_n;
					 h_sync_d1_r <= h_sync_d1_n;
					 v_sync_d2_r <= v_sync_d2_n;
					 h_sync_d2_r <= h_sync_d2_n;
            end if;
				
    end process;
	 
	 				 v_sync_d1_n <= v_sync_reg;
					 h_sync_d1_n <= h_sync_reg;
					 v_sync_d2_n <= v_sync_d1_r;
					 h_sync_d2_n <= h_sync_d1_r;

    mod_next <=  mod_reg+1;
    
    pixel_tick <= '1' when mod_reg = to_unsigned(3,2) else '0';

    
    h_end <= 
        '1' when h_count_reg = (HD+HF+HB+HR-1) else --799
        '0';
    v_end <=
        '1' when v_count_reg = (VD+VF+VB+VR-1) else --524
        '0';

    
    process(h_count_reg,h_end,pixel_tick)
        begin
            if (pixel_tick='1') then 
                if h_end='1' then 
                    h_count_next <= (others=>'0');
                else
                    h_count_next <= h_count_reg+1;
                end if;
            else
                h_count_next <= h_count_reg;
            end if;
    end process;

    
    process(v_count_reg,h_end,v_end,pixel_tick)
        begin
            if (pixel_tick='1' and h_end='1') then
                if (v_end='1') then
                    v_count_next <= (others=>'0');
                else
                    v_count_next <= v_count_reg+1;
                end if;
            else
                v_count_next <= v_count_reg;
            end if;
    end process;

    
    h_sync_next <=
        '1' when (h_count_reg >= (HD+HF))  
              and (h_count_reg <= (HD+HF+HR-1)) else 
        '0';

    v_sync_next <=
        '1' when (v_count_reg >= (VD+VF))  --490
             and (v_count_reg <= (VD+VF+VR-1)) else --491
        '0';

    
    VideoOn <= '1' when (h_count_reg < HD) and (v_count_reg < VD) else '0';


    
    Hsync <= h_sync_reg;
    Vsync <= v_sync_reg;
    xCounter <= std_logic_vector(h_count_reg);
    yCounter <= std_logic_vector(v_count_reg);
    

end Behavioral;

