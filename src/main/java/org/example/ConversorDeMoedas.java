import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorDeMoedas {

    // Método para obter taxa de câmbio da API
    public static double getExchangeRate(String from, String to) {
        try {
            String urlStr = "https://api.exchangerate-api.com/v4/latest/" + from;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            return json.getAsJsonObject("rates").get(to).getAsDouble();

        } catch (Exception e) {
            System.out.println("Erro ao obter taxa de câmbio: " + e.getMessage());
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n*******************************************");
            System.out.println(" Seja Bem-vindo(a) ao Conversor de Moedas!\n");
            System.out.println("1. Dólar (USD) -> Real (BRL)");
            System.out.println("2. Real (BRL) -> Dólar (USD)");
            System.out.println("3. Euro (EUR) -> Real (BRL)");
            System.out.println("4. Real (BRL) -> Euro (EUR)");
            System.out.println("5. Euro (EUR) -> Dólar (USD)");
            System.out.println("6. Dólar (USD) -> Euro (EUR)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção válida: ");

            opcao = sc.nextInt();

            if (opcao != 0) {
                System.out.print("Digite um valor: ");
                double valor = sc.nextDouble();

                String from = "", to = "";

                switch (opcao) {
                    case 1: from = "USD"; to = "BRL"; break;
                    case 2: from = "BRL"; to = "USD"; break;
                    case 3: from = "EUR"; to = "BRL"; break;
                    case 4: from = "BRL"; to = "EUR"; break;
                    case 5: from = "EUR"; to = "USD"; break;
                    case 6: from = "USD"; to = "EUR"; break;
                    default: System.out.println("Opção inválida!"); continue;
                }

                double taxa = getExchangeRate(from, to);
                if (taxa != -1) {
                    double convertido = valor * taxa;
                    System.out.printf("Valor %.2f [%s] corresponde ao valor final de %.2f [%s]%n", valor, from, convertido, to);
                }
            }

        } while (opcao != 0);

        sc.close();
        System.out.println("Programa encerrado!");
    }
}
