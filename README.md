# Simple-Graphic-Card
Under the supervision of Dr. A. Bohlooli

Design and Implementation of a Graphic Card to Display Images on Screens Using FPGA and VHDL
Q1: Change a given program to generate SRAM's addresses for reading and sending datas for monitor without multiplier

Q2: adding a double buffering feature to the program ( use three right bits for first buffer and other three bits for second buffer). use a button to switch buffers and initialize the first buffers with read and second one with blue. Finally send two pictures to the buffers and show them on the monitor using UART.

Q3: Change the program and add the ability to switch buffers using a software using UART.( we have used the two most significant data bits)

Q4: Write a program using Java or C# to let the user to paint on a 640*480 screen with 8 colors with these features. A send and a stop buttons, 2 buttons to reset buffers and a check box to choose the buffer. In fact this program performs as a driver for FPGA ( we don’t need the Terminal any more).

Q5: first search about Vsync, Hsync and VideoOn signals for 640*480 resolution with 60 Hz frequency and write the SyncGen module yourself.
