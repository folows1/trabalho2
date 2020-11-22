package br.edu.univas.main;

import java.util.Scanner;
import br.edu.univas.vo.*;

public class Start {
	static Scanner l = new Scanner(System.in);

	public static void main(String[] args) {
		String op;
		Jogo[] jogos = new Jogo[50];
		Time[] times = new Time[50];
		do {
			System.out.println("\n -- MENU -- ");
			System.out.println("1 – Cadastrar Time \n" + "2 – Editar Time \n" + "3 – Excluir Time \n"
					+ "4 – Cadastrar Jogo \n" + "5 – Editar Jogo \n" + "6 – Excluir Jogo \n"
					+ "7 – Listar Classificação do Campeonato \n" + "9 - Sair");
			System.out.println("\n Digite o número da opção desejada: ");
			op = l.nextLine();
			if (op.equals("1")) {
				cadastrarTime(times);
			} else if (op.equals("2")) {
				editarTime(times, jogos);
			} else if (op.equals("3")) {
				excluirTime(times, jogos);
			} else if (op.equals("4")) {
				cadastrarJogo(times, jogos);
			} else if (op.equals("5")) {
				editarJogo(times, jogos);
			} else if (op.equals("6")) {
				excluirJogo(times, jogos);
			} else if (op.equals("7")) {
				ranking(times);
			} else if (op.equals("9")) {
				System.out.println("\n \n FIM!");
				break;
			} else {

			}
		} while (true);
		l.close();
	}

	public static void cadastrarTime(Time[] times) {
		int i = 0;
		do {
			if (times[i] == null) {
				Time time = new Time();
				System.out.println("\n CADASTRO TIMES  ");
				System.out.println("Digite o nome do time: ");
				time.nome = l.nextLine();
				System.out.println("Digite a sigla UF do estado do time: ");
				time.estado = l.nextLine();
				times[i] = time;
				break;
			}
			i++;
		} while (true);
	}

	public static void editarTime(Time[] times, Jogo[] jogos) {
		listarTimes(times);
		System.out.println("Digite o nome do time que deseja EDITAR: ");
		String nome = l.nextLine();
		for (int i = 0; i < 50; i++) {
			if (times[i] != null) {
				Time time = times[i];
				if (nome.equals(time.nome)) {
					System.out.println("Digite o novo nome do time: ");
					String novo = l.nextLine();
					for (int j = 0; j < 50; j++) {
						if (jogos[j] != null && jogos[j].mandante.equals(time.nome)) {
							jogos[j].mandante = novo;
						} else if (jogos[j] != null && jogos[j].visitante.equals(time.nome)) {
							jogos[j].visitante = novo;
						}
					}
					time.nome = novo;
					System.out.println("Digite a sigla UF do time: ");
					String novoUF = l.nextLine();
					time.estado = novoUF;
					times[i] = time;
					break;
				}
			}
		}
	}

	public static void cadastrarJogo(Time[] times, Jogo[] jogos) {
		int i = 0;
		do {
			if (jogos[i] == null) {
				Jogo jogo = new Jogo();
				System.out.println("\n Cadastro de jogo");
				System.out.println("Digite o nome do TIME MANDANTE: ");
				jogo.mandante = l.nextLine();
				System.out.println("Digite o nome do TIME VISITANTE: ");
				jogo.visitante = l.nextLine();
				System.out.println("Digite os gols do time MANDANTE: ");
				jogo.golsM = l.nextInt();
				System.out.println("Digite os gols do time VISITANTE: ");
				jogo.golsV = l.nextInt();
				l.nextLine();
				jogos[i] = jogo;
				distribuirPontos(times, jogos, i);
				break;
			}
			i++;
		} while (true);
	}

	public static void editarJogo(Time[] times, Jogo[] jogos) {
		listarJogos(jogos);
		System.out.println("Digite o número do jogo que deseja editar: ");
		int i = l.nextInt() - 1;
		l.nextLine();
		Jogo jogo = jogos[i];
		System.out.println("ALTERAÇÃO DOS DADOS DO JOGO");
		System.out.println("Digite o nome do TIME MANDANTE: ");
		jogo.mandante = l.nextLine();
		System.out.println("Digite o nome do TIME VISITANTE: ");
		jogo.visitante = l.nextLine();
		System.out.println("Digite os GOLS do time MANDANTE: ");
		jogo.golsM = l.nextInt();
		System.out.println("Digite os GOLS do time VISITANTE: ");
		jogo.golsV = l.nextInt();
		l.nextLine();
		jogos[i] = jogo;
		distribuirPontos(times, jogos, i);
	}

	public static void distribuirPontos(Time[] times, Jogo[] jogos, int qJogos) {
		int m = 0, v = 0, i = 0, j = 0;
		Jogo jogo = jogos[qJogos];

		do {
			if (times[i] != null) {
				Time time = times[i];
				if (time.nome.equals(jogo.mandante)) {
					m = i;
					j++;
				} else if (time.nome.equals(jogo.visitante)) {
					v = i;
					j++;
				}
				if (j == 2) {
					break;
				}
			}

			i++;
		} while (true);
		if (jogo.golsM > jogo.golsV) {
			times[m].pontos = times[m].pontos + 3;
			times[m].saldo = times[m].saldo + (jogo.golsM - jogo.golsV);
			times[v].saldo = times[v].saldo + (jogo.golsV - jogo.golsM);
		} else if (jogo.golsV > jogo.golsM) {
			times[v].pontos = times[v].pontos + 3;
			times[m].saldo = times[m].saldo + (jogo.golsM - jogo.golsV);
			times[v].saldo = times[v].saldo + (jogo.golsV - jogo.golsM);
		} else if (jogo.golsV == jogo.golsM) {
			times[m].pontos = times[m].pontos + 1;
			times[v].pontos = times[v].pontos + 1;
		}
	}

	public static void listarJogos(Jogo[] jogos) {
		int i = 0;
		System.out.println("Listagem de Jogos Cadastrados: ");
		do {
			if (i == 50) {
				break;
			}
			if (jogos[i] != null) {
				Jogo jogo = jogos[i];
				System.out.println((i + 1) + ".      " + jogo.mandante + " " + jogo.golsM + " x " + jogo.golsV + " "
						+ jogo.visitante);
			}

			i++;
		} while (true);
	}

	public static void listarTimes(Time[] times) {
		int i = 0;
		System.out.println("Lista de Times Cadastrados: ");
		do {
			if (i == 50) {
				break;
			}
			if (times[i] != null) {
				Time time = times[i];
				System.out.println(time.nome);
			}

			i++;
		} while (true);
	}

	public static void excluirJogo(Time[] times, Jogo[] jogos) {
		listarJogos(jogos);
		System.out.println("Digite o índice do jogo que deseja excluir: ");
		int i = l.nextInt() - 1;
		l.nextLine();
		tirarPontos(i, jogos, times);
		jogos[i] = null;
	}

	public static void excluirTime(Time[] times, Jogo[] jogos) {
		listarTimes(times);
		int i = 0;
		System.out.println("Digite o nome do time que deseja excluir: ");
		String nome = l.nextLine();
		do {
			if (times[i] != null) {
				Time time = times[i];
				if (nome.equals(time.nome)) {
					break;
				}
			}
			i++;
		} while (true);
		Time time = times[i];
		for (int j = 0; j < 50; j++) {
			if (jogos[j] != null) {
				Jogo jogo = jogos[j];
				if (jogo.mandante.equals(time.nome)) {
					tirarPontos(j, jogos, times);
					jogos[j] = null;
				} else if (jogo.visitante.equals(time.nome)) {
					tirarPontos(j, jogos, times);
					jogos[j] = null;
				}
			}
		}
		times[i] = null;
	}

	public static void tirarPontos(int i, Jogo[] jogos, Time[] times) {
		Jogo jogo = jogos[i];
		int j = 0, m = 0, v = 0, k = 0;
		do {
			if (times[j] != null) {
				Time time = times[j];
				if (time.nome.equals(jogo.mandante)) {
					m = j;
					k++;
				} else if (time.nome.equals(jogo.visitante)) {
					v = j;
					k++;
				}
				if (k == 2) {
					break;
				}
			}
			j++;
		} while (true);
		if (jogo.golsM > jogo.golsV) {
			times[m].pontos = times[m].pontos - 3;
			times[m].saldo = times[m].saldo - (jogo.golsM - jogo.golsV);
			times[v].saldo = times[v].saldo - (jogo.golsV - jogo.golsM);
		} else if (jogo.golsV > jogo.golsM) {
			times[v].pontos = times[v].pontos - 3;
			times[m].saldo = times[m].saldo - (jogo.golsM - jogo.golsV);
			times[v].saldo = times[v].saldo - (jogo.golsV - jogo.golsM);
		} else if (jogo.golsV == jogo.golsM) {
			times[m].pontos = times[m].pontos - 1;
			times[v].pontos = times[v].pontos - 1;
		}
	}

	public static void ranking(Time[] times) {
		Time[] tabela = new Time[50];
		int x = passarValores(times, tabela);
		ordenar(tabela, x);
		imprimirTabela(tabela, x);
	}

	public static int passarValores(Time[] times, Time[] tabela) {
		int j = 0;
		for (int i = 0; i < 50; i++) {
			if (times[i] != null) {
				Time time = times[i];
				Time time2 = new Time();
				time2.nome = time.nome;
				time2.estado = time.estado;
				time2.pontos = time.pontos;
				time2.saldo = time.saldo;
				tabela[j] = time2;
				j++;
			}
		}
		return j;
	}

	public static void ordenar(Time[] tabela, int x) {
		for (int i = 0; i < x - 1; i++) {
			for (int j = 0; j < x - 1 - i; j++) {
				if (tabela[j] != null && tabela[j + 1] != null && tabela[j].pontos >= tabela[j + 1].pontos
						&& tabela[j].saldo > tabela[j + 1].saldo) {
					Time timeAux = tabela[j];
					tabela[j] = tabela[j+1];
					tabela[j+1] = timeAux;
				}
			}
		}
	}
	
	public static void imprimirTabela(Time[] tabela, int x) {
		System.out.println(" ----  CLASSIFICAÇÃO  ---- \n");
		int j = 1;
		for(int i = x; i >= 0; i--) {
			if(tabela[i] != null) {
				Time time = tabela[i];
				System.out.println(j+"º "+time.nome+" - "+time.pontos+" pontos - saldo: "+time.saldo);
				j++;
			}
		}
	}

}
