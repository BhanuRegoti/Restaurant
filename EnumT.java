

public class EnumT {

    enum test {a,b,c};
    
public static void main(String args[]) {

    String yo = "b"; //Take input from that text field

switch(test.valueOf(yo.trim())) {
    case a: System.out.println("a"); break;
    case b: System.out.println("b"); break;
    case c:System.out.println("c"); break;
}
}
}