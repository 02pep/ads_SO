import java.util.*;

public class Main {
    static int max_tempo_execucao = 999;
    static ArrayList<Processo> processos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();
        while (true) {
            System.out.println("Selecione uma opção: ");
            System.out.println("1) FCFS");
            System.out.println("2) SJF Não-Preemptivo");
            System.out.println("3) SJF Preemptivo");
            System.out.println("4) Criar processo(s)");
            System.out.println("5) Exibir lista de processos");
            System.out.println("6) Sair do programa");
            int opcao = teclado.nextInt();
            if (opcao == 1) {
                FCFS();
            } else if (opcao == 2) {
                SJFNaoPreemptivo();
            } else if (opcao == 3) {
                SJFPreemptivo();
            } else if (opcao == 4) {
                criarProcesso();
            } else if (opcao == 5) {
                exibirLista();
            } else if (opcao == 6){
                break;
            }
        }

    }

    public static void SJFNaoPreemptivo() {
        if (processos.isEmpty()) {
            System.out.println("Nenhum processo foi criado!!");
        } else {
            for (Processo p : processos) {
                p.tempo_restante = p.tempo_execucao;
                p.tempo_espera = 0;
            }
            int tempo = 0;
            int nProcessos = 0;
            ArrayList<Processo> listaDeProntos = new ArrayList<>();
            System.out.println("Tempo\tId do processo\tTempo restante");
            System.out.println("------------------------------------");
            while (tempo < max_tempo_execucao && nProcessos < processos.size()) {
                for (Processo p : processos) {
                    if (p.tempo_chegada == tempo) {
                        listaDeProntos.add(p);
                    }
                }
                if (!listaDeProntos.isEmpty()) {
                    Comparator<Processo> comparador = Comparator.comparing(Processo::getTempo_execucao);
                    Processo menorTempoExec = listaDeProntos.stream().min(comparador).get();
                    System.out.println(tempo + "\t\t" + menorTempoExec.id + "\t\t\t\t" + (menorTempoExec.tempo_restante-1));
                    tempo++;
                    menorTempoExec.tempo_restante--;
                    if (menorTempoExec.tempo_restante == 0) {
                        listaDeProntos.remove(menorTempoExec);
                        nProcessos++;
                    }
                    for (Processo p : processos) {
                        if (!menorTempoExec.equals(p) && p.tempo_restante!=0) {
                            p.tempo_espera++;
                        }
                    }
                }

            }
            int tempoEspera = 0;
            for (Processo p : processos) {
                tempoEspera+=p.tempo_espera;
                System.out.println("Tempo de espera do processo [" + p.id + "]: " + p.tempo_espera);
            }
            System.out.println("Tempo médio de espera: " + (double) tempoEspera/ processos.size());
        }
    }

    public static void criarProcesso() {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Quantos processos quer criar?");
        int n_processo = teclado.nextInt();
        System.out.println("Configurar aleatóriamente? [1 - Sim; 0 - Não]");
        int a = teclado.nextInt();
        if (a == 1) {
            for (int i = 0; i < n_processo; i++) {
                Processo p = new Processo();
                p.id = processos.size();
                p.tempo_execucao = random.nextInt(9) + 1;
                p.tempo_chegada = random.nextInt(9)+1;
                processos.add(p);
            }
        } else {
            for (int i = 0; i < n_processo; i++) {
                Processo p = new Processo();
                p.id = processos.size();
                System.out.println("Digite o tempo de execução para o processo[" + p.id + "]");
                p.tempo_execucao = teclado.nextInt();
                System.out.println("Digite o tempo de chegada para o processo[" + p.id + "]");
                p.tempo_chegada = teclado.nextInt();
                processos.add(p);
            }
        }
    }

    public static void FCFS() {
        if (processos.isEmpty()) {
            System.out.println("Nenhum processo foi criado!!");
        } else {
            int tempo = 0;
            int tempo_espera_total = 0;
            System.out.println("Tempo\tId do processo\tTempo restante");
            System.out.println("------------------------------------");
            for (Processo processo : processos) {
                int tempo_processo = 0;
                while (tempo_processo < processo.tempo_execucao && tempo < max_tempo_execucao) {
                    {
                        System.out.println(tempo + "\t\t" + processo.id + "\t\t\t\t" + (processo.tempo_execucao - tempo_processo - 1));
                        tempo++;
                        tempo_processo++;
                    }
                }
                if (processos.indexOf(processo) != 0) {
                    processo.tempo_espera = tempo - tempo_processo;
                }
            }
            for (Processo processo : processos) {
                System.out.println("Tempo de espera do processo[" + processo.id + "]: " + processo.tempo_espera);
                tempo_espera_total = +processo.tempo_espera;
            }
            if (!processos.isEmpty()) {
                System.out.println("Tempo médio de espera: " + (double) tempo_espera_total / processos.size());
            }
        }
        limpaTempoEspera();
    }
    public static void limpaTempoEspera() {
        for (Processo processo : processos) {
            processo.tempo_espera = 0;
        }
    }

    public static void exibirLista() {
        for (Processo processo : processos) {
            System.out.println("Processo[" + processo.id + "] - Tempo de execução: " + processo.tempo_execucao + " - Tempo de chegada: " + processo.tempo_chegada);
        }
    }

    public static void SJFPreemptivo() {
        if (processos.isEmpty()) {
            System.out.println("Nenhum processo foi criado!!");
        } else {
            for (Processo p : processos) {
                p.tempo_restante = p.tempo_execucao;
                p.tempo_espera = 0;
            }
            int tempo = 0;
            int nProcessos = 0;
            ArrayList<Processo> listaDeProntos = new ArrayList<>();
            System.out.println("Tempo\tId do processo\tTempo restante");
            System.out.println("------------------------------------");
            while (tempo < max_tempo_execucao && nProcessos < processos.size()) {
                for (Processo p : processos) {
                    if (p.tempo_chegada == tempo) {
                        listaDeProntos.add(p);
                    }
                }
                if (!listaDeProntos.isEmpty()) {
                    Comparator<Processo> comparador = Comparator.comparing(Processo::getTempo_restante);
                    Processo menorTempoExec = listaDeProntos.stream().min(comparador).get();
                    System.out.println(tempo + "\t\t" + menorTempoExec.id + "\t\t\t\t" + (menorTempoExec.tempo_restante-1));
                    tempo++;
                    menorTempoExec.tempo_restante--;
                    if (menorTempoExec.tempo_restante == 0) {
                        listaDeProntos.remove(menorTempoExec);
                        nProcessos++;
                    }
                    for (Processo p : processos) {
                        if (!menorTempoExec.equals(p) && p.tempo_restante!=0) {
                            p.tempo_espera++;
                        }
                    }
                }

            }
            int tempoEspera = 0;
            for (Processo p : processos) {
                tempoEspera+=p.tempo_espera;
                System.out.println("Tempo de espera do processo [" + p.id + "]: " + p.tempo_espera);
            }
            System.out.println("Tempo médio de espera: " + (double) tempoEspera/ processos.size());
        }
    }
}