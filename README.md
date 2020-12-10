# OOP_Project
A Java Application To Encrypt And Decrypt A Message Using ADFGVX Cypher

		#The ADFGVX Cipher
														
The ADFGVX Cipher was a field cipher used by the German Army during World War I and was an extension of an earlier cipher called the ADFGX Cipher. The cipher was a fractionating transposition cipher which combined a modified Polybius square with a single columnar transposition.

This cipher uses a keyword and a Polybius Square( A 6X6 Square containing all the letters from A-Z and numbers 0-9). The following is an example of a Polybius Square.

  A D F G V X
A P H 0 Q G 6
D 4 M E A 1 Y
F L 2 N O F D
G X K R 3 C V
V S 5 Z W 7 B
X J 9 U T I 8

		#To Encrypt a plain text: 

(1) Locate the letter in the matrix and read off the letter on the far left side on the same row, followed by the letter at the top in the same column.
	Thus each plain text letter is replaced by two cipher text letters (the cipher text is twice as long as plain text). 
(2) Form a matrix using the keyword as first row and the enciphered letters underneath.
(3) Columnar transposition by sorting the keyword alphabetically and moving the columns as we go.
(4) The final cipher text is formed by reading off the columns.

		#To Decrypt a cipher text:

(1) Form a matrix with first row as sorted keyword and followed by placing the cipher text letters column-wise.
	The remaining rows can be determined by (message-length/code-word-length).[In this application it is determined by (int)Math.ceil((double)text.length()/key.length())].
(2) Doing a columnar transposition use the original keyword.
(3) Reading off the rows. 
(4) For each pair of letters in the cipher text look in the Polybius Square,for the first one the column in the far left and for the other the first row,
	and find out the letter from the matrix which is in the same row and column.

		#Java Application

The Java application contains in the directory can be used to encrypt and decrypt short messages as well as text files.

It has a menu (Menu.java) which asks the user to input a keyword, message/file to transform.

It also contains files:Cypher.java , Parser.java, and Runner.java.

Cypher.java contains methods to encrypt and decrypt user inputs.

Parser.java handles text files to encrypt and decrypt by using the same methods in Cypher.java . Encrypted file can be found in encoded.txt and decrypted in decoded.txt .

Runner.java contains the main method.


		#Requirement
		
A Java platform.	

		#Installation

From an IDE: navigate to the Runner class and run as a java application.

From command line: 

	To compile: javac ie/gmit/dip/*.java
	To run: java ie.gmit.dip.Runner

		
		#Other Information

In-order to perform the application the following things should be satisfied:		

(1)The keyword should contain unique characters and must not be empty.

(2)The message/file to encrypt must contain at least one alphabet or number.

(3)The message/file to decrypt must contain only characters {A,D,F,G,V,X}; and a special character *.

The application is tested and is successful on different input instances.

		#Limitation
		
The encoded and decoded messages will be converted to uppercase without any spaces between words.		
