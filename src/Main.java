/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {

        int userChoice = 1;
        while (userChoice != 0) {

            userChoice = menu();

            switch (userChoice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
    }
    public static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println("\n\n");
        System.out.println("Choose option");
        System.out.println("-------------------------");
        System.out.println("1 - ");
        System.out.println("2 - ");
        System.out.println("3 -  ");
        System.out.println("4 - Export data");
        System.out.println("0 - ");

        selection = input.nextInt();
        return selection;
    }
}
