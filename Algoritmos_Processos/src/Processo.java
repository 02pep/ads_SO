public class Processo {
    int id;
    int tempo_espera = 0;
    int tempo_execucao;
    int tempo_chegada;
    int tempo_restante;
    int prioridade;

    public int getTempo_restante() {
        return tempo_restante;
    }
    public int getTempo_execucao() {
        return tempo_execucao;
    }

    public int getPrioridade() {
        return prioridade;
    }
}