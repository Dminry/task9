import java.io.File;
import java.util.Scanner;

public class Main {
  //static   File saveFile = new File("basket.json");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {10, 15, 20};
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        File saveFile = new File("basket.json");
        Basket basket;
        if (saveFile.exists()) {
            System.out.println();
            basket = Basket.loadFromJSONFile(saveFile);
            basket.printCart();
        } else {
            System.out.println("Создаем новую корзину..");
            System.out.println();
            basket = new Basket(products, prices);
        }
        ClientLog clientLog = new ClientLog();

        while (true) {
            System.out.println("Выберите товар и количество или введите `Итого'");
            String input = scanner.nextLine();
            if ("Итого".equals(input)) {
                clientLog.exportAsCSV(new File("log.csv"));
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            clientLog.log(productNumber,productCount);
            basket.saveJSON(saveFile);
        }
        basket.printCart();
        basket.saveJSON(saveFile);
    }
}