
public class HuffmanTree{
	public HuffmanTree right_child, root, left_child = null;
	public int totalWeight;  // weight of tree, sum of the frequencies
    public char letter;
    public String encoding = ""; 

    public HuffmanTree(char c, int f){
    totalWeight = f;
    letter = c;
    encoding = "";
    }
    
    public HuffmanTree(char c){
    totalWeight = 1;
    letter = c;
    encoding = "";
    }
    
    
    public HuffmanTree(HuffmanTree left, HuffmanTree right){
    	totalWeight = left.totalWeight + right.totalWeight;
    	left_child = left;
    	right_child = right;
    	left.root = this;
    	right.root = this;
    	encoding = "";
    }
}
