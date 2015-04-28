# To Run
To run This program, Compile HuffmanCompression.java

javac HuffmanCompression.java

To run the main in HuffmanCompression, and Specify what you want to compress by entering what you want to compress at the end of the command (EOF)
java HuffmanCompression crimeandpunishment.txt
will import the txt in the file crimeandpunishment.txt and compress it

OR

java HuffmanCompression the string you want to compress, it will compress this.
will take all the strings that follow the execution command, and compress that string.



# DESCRIPTION OF CODE
HuffmanCompression contains the main class that does the following in order:
-gets the text it wants to compress
-iterates through the text, popping the first char off the text at each iteration
-Creates a HuffmanNode of the popped Char
-adds the created Node to a vector of HuffmanNodes, (or increments the Node if it already exists.)
-Sorts the Vector once it is finished reading characters from the text
-builds a HuffmanTree from vector, sorting each time a node is created.
-Transverses through the tree, keeping track of path taken, and assigning the encoding generated from path to the corresponding char when it reaches a leaf.
-outputs the list of Chars, their frequencies, and their unique encoding.
-outputs compression statistics. (Assumes one char is 8 bits long)

HuffmanNode
is a custom object that contains representations of
-character
-Frequency/weight
-Left child
-Right child
-Root
-encoding

# Discoveries
I initially used a hash-table to represent characters and their corresponding frequencies. However I figured out that you have some trouble sorting hash tables. So I created a custom data-structure to represent a Node. I learned a lot about the Huffman tree encoding. I particularly like how the encoding is unique for whatever input text it compiles. I initially did not know that, until Dr. Iba helped me understand how the Huffman tree gets constructed. It was also nice switching back to another language i advent used in a while. As a result, I feel more comfortable with links/pointers, binary trees, and the Huffman Tree. I have grown my appreciation for the creativity and uniqueness that David Huffman embodied when he developed this efficient algorithm for his information theory course term paper.


# Acknowledgements
Thanks to Dr. Iba for aiding me in understanding the Binary Tree / Huffman Tree construction algorithm, and his incite in transversing through the tree to determine the encoding. 
I distributed the help I received to others in the class.