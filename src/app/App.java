package app;

import java.util.Scanner;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class App {
  private static Random rand = new Random();
  private static Scanner teclado = new Scanner(System.in);
  private static List<Aposta> lista = new LinkedList<>();
  private static int MAIORNUMERO = 60;
  private static int TAMANHODAAPOSTA = 5;

  public static void main(String[] args) {
    boolean encerrar = false;

    while (!encerrar) {
      int opcao;

      System.out.println("BEM-VINDO(A) A CASA DE APOSTAS MEGADELL!" + "\nDIGITE A OPÇÃO DESEJADA:");
      System.out.println("[1] INICIAR APOSTAS");
      System.out.println("[2] REALIZAR SORTEIO");
      System.out.println("[3] PREMIAÇÃO");
      System.out.println("[4] CONSULTAR RELATÓRIO");
      System.out.println("[0] ENCERRAR");
      opcao = teclado.nextInt();
      teclado.nextLine();

      switch (opcao) {
        case 1:
          iniciarAposta();
          break;
        case 2:
          realizarSorteio();
          break;
        case 3:

          break;
        case 4:

          break;
        case 0:
          encerrar = true;

          break;

        default:
          break;
      }

    }

    teclado.close();
  }

  private static void iniciarAposta() {
    System.out.println("O QUE VOCE DESEJA FAZER?");
    System.out.println("[1] REGISTRAR NOVA APOSTA");
    System.out.println("[2] CONSULTAR APOSTAS REGISTRADAS");
    int opcao = teclado.nextInt();
    teclado.nextLine();

    if (opcao == 1) {
      System.out.println("Digite seu cpf: ");
      String cpf = teclado.nextLine();
      System.out.println("Digite seu nome: ");
      String nome = teclado.nextLine();
      Aposta aposta = criaAposta(cpf);
      aposta.nome = nome;
      lista.add(aposta);

      for (Aposta item : lista) {
        item.print();
      }
    } else if (opcao == 2) {
      for (Aposta item : lista) {
        item.print();
      }
    } else {
      System.out.println("OPÇÃO INVÁLIDA TENTE NOVAMENTE!: " + opcao);
    }
  }

  private static Aposta criaAposta(String cpf) {
    System.out.println("GERAR APOSTA SURPRESINHA?" + "\n [1] SIM" + "\n [2] NÃO");
    int opcao = teclado.nextInt();
    teclado.nextLine();

    int cont = 0;
    Aposta aposta = new Aposta();
    aposta.cpf = cpf;

    while (cont < TAMANHODAAPOSTA) {
      if (opcao == 1) {
        int aleatorio = rand.nextInt(MAIORNUMERO);
        if (!aposta.numeroNaLista(aleatorio)) {
          aposta.numeros.add(aleatorio);
          cont++;
        }
      } else if (opcao == 2) {
        System.out.println("DIGITE O " + (cont + 1) + "º NUMERO DA APOSTA: ");
        int numero = teclado.nextInt();
        teclado.nextLine();

        if (!aposta.numeroNaLista(numero)) {
          aposta.numeros.add(numero);
          cont++;
        } else {
          System.out.println("Número já existente na aposta.");
        }
      }
    }

    return aposta;
  }

  private static void realizarSorteio() {
    List<Integer> sorteio = new LinkedList<>();
    int cont = 0;

    while (cont < TAMANHODAAPOSTA) {
      int aleatorio = rand.nextInt(MAIORNUMERO);

      boolean existente = false;

      for (Integer item : sorteio) {
        if (aleatorio == item) {
          existente = true;
          break;
        }
      }

      if (!existente) {
        sorteio.add(aleatorio + 1);
        cont++;
      }
    }

    int maiorAcertos = 0;

    while (maiorAcertos == 0) {
      HashMap<Aposta, Integer> resultado = new LinkedHashMap<>();

      for (Aposta aposta : lista) {
        int acertos = 0;
        for (Integer numero : aposta.numeros) {
          for (Integer item : sorteio) {
            if (item == numero) {
              acertos++;
              break;
            }
          }
        }
        resultado.put(aposta, acertos);
        if (acertos > maiorAcertos) {
          maiorAcertos = acertos;
        }
      }

      if (maiorAcertos == 0) {
        System.out.println("Não houve acertadores com " + sorteio.size() + ". Sorteando mais um número.");
        if (sorteio.size() == 25) {
          System.out.println("Não houve vencedores mesmo após 25 números sorteados;");
        }

        boolean existente = false;
        while (!existente) {
          int aleatorio = rand.nextInt(MAIORNUMERO);

          for (Integer item : sorteio) {
            if (aleatorio == item) {
              existente = true;
              break;
            }
          }

          if (!existente) {
            sorteio.add(aleatorio + 1);
            break;
          }
        }

      } else {
        StringBuilder sb = new StringBuilder();
        sb.append("Números sorteados: ");
        Collections.sort(sorteio);
        for (int i = 0; i < sorteio.size(); i++) {
          sb.append(sorteio.get(i));
          if (i + 1 < sorteio.size()) {
            sb.append(", ");
          }
        }
        System.out.println(sb.toString());

        for (Map.Entry<Aposta, Integer> entry : resultado.entrySet()) {
          if (entry.getValue() == maiorAcertos) {
            System.out.println("Aposta vencedora com " + maiorAcertos + " acertos foi:");
            entry.getKey().print();
          }
        }
      }
    }
  }

}
