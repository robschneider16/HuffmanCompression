import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;


public class HuffmanCompression {
	String Filename;
	FileReader text;
    BufferedReader BufferedText;
    Vector<HuffmanTree> CharacterProbabilites = new Vector<HuffmanTree>();
    boolean finished = false;
    HuffmanTree CompressionTreeRoot = null;
    
    //get the text file from user input. Save as a buffered reader
    public void get_text_from_file(String input) throws FileNotFoundException{
        Filename = input;
        text = new FileReader(Filename);
        BufferedText = new BufferedReader(text);
    }
    //get the string of text entered at EOF as the input. create a buferd reader.
    public void get_text_from_input(String input){
    	BufferedText = new BufferedReader(new StringReader(input));
    }
    
    //walk through text and compute character frequencies,
    public void compute_character_frequencies() throws IOException{
        while(finished == false){
            extract_char();
        }
    }
    //extract one char at a time. (loop until empty) and place extracted value into NodeArray
    public void extract_char() throws IOException{
        int x = BufferedText.read();
        char letter = (char)x;
        boolean ExistInArrayAlready = false;
        int ExistingCharIndex = 0;
        //if the bufferedReader is empty, returns -1 and stops extracting
        if(x == -1){
            finished = true;
        }
        else{ //add char to the node array, or increment existing frequency if it already exists.
        	for(int i=0;i < CharacterProbabilites.size();i++){
        		if (CharacterProbabilites.get(i).letter == letter){
        			ExistInArrayAlready = true;
        			ExistingCharIndex = i;
        			break;
        		}
        	}
            if(ExistInArrayAlready == true){
                //increment existing value
            	 int y = CharacterProbabilites.get(ExistingCharIndex).totalWeight;
            	 CharacterProbabilites.elementAt(ExistingCharIndex).totalWeight = y+1;
            }
            else{
                CharacterProbabilites.add(new HuffmanTree(letter));
            }
        }
    }
    
	//sort the VECTOR by increasing/decreasing frequency SEE HuffmanTree
	public static void sort_by_HuffmanTreeWeight(Vector<HuffmanTree> A, int p, int r ){//custom quicksort
		if(p < r){
			int q = partitionHT(A, p, r);
			sort_by_HuffmanTreeWeight(A, p, q-1);
			sort_by_HuffmanTreeWeight(A, q+1, r);
		}
	 }
	public static int partitionHT(Vector<HuffmanTree> A, int p, int r){
		HuffmanTree x = A.get(r); //Pivot
		int i = p-1;
		for(int j = p;j <= r-1; j++){
			if(A.get(j).totalWeight <= x.totalWeight){
				i = i+1;
				exchangeHT(A, i,j);
			}
		}
		exchangeHT(A, i+1, r);
		return i+1;
	}
	public static void exchangeHT(Vector<HuffmanTree> A, int i, int j){
		HuffmanTree C = A.get(i);
		HuffmanTree B = A.get(j);
		A.set(i, B);
		A.set(j, C);
	}
    
	
    //Construct the Huffman compression tree.
	//post:one root node with all characters as children. = CompressionsTreeRoot
    public void construct_huffman_tree(){
    	sort_by_HuffmanTreeWeight(CharacterProbabilites, 0, CharacterProbabilites.size()-1);
    	Vector<HuffmanTree> HuffmanTreeBuildingVector = new Vector<HuffmanTree>();
    	//populate HuffmanTreeBuildingVector
    	
    	for(int i = 0; i < CharacterProbabilites.size(); i++){
    		HuffmanTreeBuildingVector.add(CharacterProbabilites.get(i));//so the original data isnt manipulated and lost
    	}    	
    	int numOfNodes = HuffmanTreeBuildingVector.size();
    	
    	while(numOfNodes != 1){//if there is one item left, then we have reached the top of our tree.
    		//Extract smallest two items
    		HuffmanTree first = HuffmanTreeBuildingVector.get(0);//smallest weighted items are in the front of the vector
    		HuffmanTree second = HuffmanTreeBuildingVector.get(1);
    		//build a new node
    		HuffmanTree g = new HuffmanTree(first, second); 
    		//add new node to back into the tree
    		HuffmanTreeBuildingVector.set(0, g);
    		//remove them from the vector
    		HuffmanTreeBuildingVector.remove(1);//remove the second element first,
    		//sort the HuffmanTreeBuildingVector again for better compression.
    		numOfNodes = HuffmanTreeBuildingVector.size();
    		sort_by_HuffmanTreeWeight(HuffmanTreeBuildingVector, 0, numOfNodes-1);
    	}
    	CompressionTreeRoot = HuffmanTreeBuildingVector.get(0);
    	transverse_tree(CompressionTreeRoot);
    }
    
    //compute encoding by transversing through tree until a node is reached.
    public void transverse_tree(HuffmanTree HTNode){
    	//if leaf(no children), then found a char, and set the encoding.
    	String code = HTNode.encoding;
    	if(HTNode.left_child == null && HTNode.right_child == null){
    		char letter = HTNode.letter;
    		//System.out.print(letter);
    		//System.out.println(code);
    		for(int i=0;i < CharacterProbabilites.size();i++){
        		if (CharacterProbabilites.elementAt(i).letter == letter){
        			CharacterProbabilites.elementAt(i).encoding = code;
        		}
    		}    		
    	}else{
       //else expand left and right HTnodes, and add on a 1 or 0 to their encoding if you take the left or right path
    		HTNode.left_child.encoding  = code + "0";
	    	HTNode.right_child.encoding = code +  "1";
	    	transverse_tree(HTNode.left_child);
	    	transverse_tree(HTNode.right_child);
    	}
    }
    
    
    
    
	public static void main (String[] args) throws Exception{
		HuffmanCompression test = new HuffmanCompression();
		if (args.length >= 2){ //if user entered more than 1 string at EOf, then the string is not a filename. treat it as a whole string of test we want to encode.
			String ConCatArgs = "";
			for(int i = 0; i < args.length; i++){
				ConCatArgs = ConCatArgs + args[i];
			}
			test.get_text_from_input(ConCatArgs);
			System.out.println("Encoding text from typed EOF string input");
		}else{//user entered one string that represents the name of file we want to encode.
			test.get_text_from_file(args[0]);
			System.out.println("Encoding text from EOF filename given");
		}
		test.compute_character_frequencies();
		test.construct_huffman_tree();
		test.transverse_tree(test.CompressionTreeRoot);
		int uncompressedsum = 0;
		int compressedsum = 0;	
		
		System.out.println("Char_____Freq___________Encoding______________________");
		for(int i= 0;i < test.CharacterProbabilites.size();i++){
			char y = test.CharacterProbabilites.elementAt(i).letter; 		//char at index i
			int z = test.CharacterProbabilites.elementAt(i).totalWeight;	//freq of char and i
			uncompressedsum = uncompressedsum + (z*8);						//compute the sum of the number of bits in the original file, assuming 1 char = 8 bits
			String d = test.CharacterProbabilites.elementAt(i).encoding;	//get the encoding of the char at index i
			int k = d.length();												//the length of the encoding string = how many bits the char will be represented by
			compressedsum = compressedsum + (k*z);							//compute the sum of the number of bits in the compressed file
			System.out.printf("char "  + y + "\t Freq = "+ z + "\tencoding = " + d + "\n");
		}
		System.out.println("Number of bits in uncompressed file \t"+ uncompressedsum);
		System.out.println("Number of bits in compressed file   \t"+ compressedsum);
		double percent = (double)compressedsum / (double)uncompressedsum * 100; 	//% compression.
		System.out.println("Percent compression\t \t \t"+ (float)percent + "%" );	
	}
}