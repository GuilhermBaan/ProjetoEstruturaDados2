package Model;

import java.util.*;

public class MapaCidades {
    private TreeSet<Cidade> cidades = new TreeSet<>();
    private HashMap<Cidade, Set<Rota>> rotas = new HashMap<>();

    public void adicionarCidade(Cidade cidade) {
        cidades.add(cidade);
        rotas.putIfAbsent(cidade, new HashSet<>());
    }

    public void conectarCidades(Cidade c1, Cidade c2, int distancia) {
        rotas.get(c1).add(new Rota(c2, distancia));
        rotas.get(c2).add(new Rota(c1, distancia));
    }

    public void listarConexoes(Cidade cidade) {
        System.out.println("Conexões de " + cidade.getNome() + ":");
        Set<Rota> conexoes = rotas.get(cidade);
        if (conexoes == null || conexoes.isEmpty()) {
            System.out.println("Nenhuma conexão.");
            return;
        }
        for (Rota rota : conexoes) {
            System.out.println(rota);
        }
    }

    public boolean existeCaminho(Cidade origem, Cidade destino) {
        Set<Cidade> visitados = new HashSet<>();
        return busca(origem, destino, visitados);
    }

    private boolean busca(Cidade atual, Cidade destino, Set<Cidade> visitados) {
        if (atual.equals(destino)) return true;
        visitados.add(atual);
        for (Rota rota : rotas.getOrDefault(atual, new HashSet<>())) {
            if (!visitados.contains(rota.getDestino())) {
                if (busca(rota.getDestino(), destino, visitados)) return true;
            }
        }
        return false;
    }

    public void listarCidadesSemConexao() {
        for (Cidade cidade : cidades) {
            if (rotas.getOrDefault(cidade, Collections.emptySet()).isEmpty()) {
                System.out.println(cidade);
            }
        }
    }

    public Cidade getCidadeMaisPopulosa() {
        return cidades.stream().max(Comparator.comparingInt(Cidade::getPopulacao)).orElse(null);
    }
}