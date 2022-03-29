
public class Main {
    public static void main(String[] args) {
	Save test = new Save("./log.txt");

	Integer a = 13412;
	
	test.write(a);
	test.read();
    }
}
