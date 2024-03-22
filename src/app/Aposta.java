package app;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Aposta {
  public static int _codigo = 1;

  private int codigo;
  public String nome;
  public String cpf;
  public List<Integer> numeros = new LinkedList<>();

  public Aposta() {
    this.codigo = _codigo++;
  }

  public int getCodigo() {
    return this.codigo;
  }

  public boolean numeroNaLista(Integer procura) {
    for (Integer numero : numeros) {
      if (numero == procura) {
        return true;
      }
    }

    return false;
  }

  public void print() {
    System.out.println("Aposta " + codigo + " de [" + cpf + ", " + nome + "]");
    Collections.sort(numeros);
    StringBuilder sb = new StringBuilder();
    sb.append("Numeros: ");
    for (int i = 0; i < numeros.size(); i++) {
      sb.append(numeros.get(i));
      if (i + 1 < numeros.size()) {
        sb.append(", ");
      }
    }
    System.out.println(sb.toString());
  }

}
