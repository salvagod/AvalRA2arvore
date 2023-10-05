import java.util.Random;
import java.util.Scanner;

class Node { // Classe Node
    int info;
    Node esquerda;
    Node direita;

    public Node(int info) { 
        this.info = info;
        this.esquerda = null;
        this.direita = null;
    }
}

class ArvoreBinaria { // Classe Árvore Binária
    Node raiz; // Inicialização da raiz

    public ArvoreBinaria() { // Inicialização da classe, raiz sendo vazia
        this.raiz = null;
    }

    public void inserir(int valor) { // Método de inserção
        if (raiz == null) { // Se a raiz for nula, recebe o primeiro valor inserido
            raiz = new Node(valor);
            return;
        }

        Node atual = raiz; // O nó atual na checagem recebe esse primeiro valor na primeira execução

        while (true) { // Loop para fazer a definição para qual lado da árvore o valor irá
            if (valor < atual.info) {  // Se o valor a ser inserido for menor que o valor do nó atual, e não houver nós filhos à esquerda do nó atual, colocará esse valor na esquerda
                if (atual.esquerda == null) {
                    atual.esquerda = new Node(valor);
                    return;
                }
                atual = atual.esquerda;
            } 

            else { // Caso o valor a ser inserido for menor que o valor do nó atual, e não houver nós filhos à direita do nó atual, colocará esse valor na direita
                if (atual.direita == null) {
                    atual.direita = new Node(valor);
                    return;
                }
                atual = atual.direita;
            }
        }
    }

    public void inserirValoresGerados(int quantidade) { // Método para gerar os valores aleatório e inserí-los na árvore
        Random random = new Random(); // Instância da classe random

        for (int i = 0; i < quantidade; i++) {
            int valor = random.nextInt(100); // Gera um valor aleatório entre 0 e 99
            inserir(valor); // Chama a função para inserção
        }
    }

    public void remover(int valor) { // Função para remoção
        raiz = removerElemento(raiz, valor); // Chama a função para remover o elemento de acordo com a raiz e o valor a ser procurado
    }

    private Node removerElemento(Node no, int valor) {
        if (no == null) { // Caso o nó for nulo, retorna ele próprio
            return no;
        }

        if (valor < no.info) { // Caso o valor for menor que a raiz atual, fará uma chamada recursiva considerando a raiz como o nó da esquerda
            no.esquerda = removerElemento(no.esquerda, valor);
        } 

        else if (valor > no.info) { // Caso o valor for maior que a raiz atual, fará uma chamada recursiva considerando a raiz como o nó da direita
            no.direita = removerElemento(no.direita, valor);
        } 
        
        else { // Caso o elemento seja o procurado
            if (no.esquerda == null) { // Se o nó da esquerda for nulo, retornará o nó direito
                return no.direita;
            } 
            
            else if (no.direita == null) { // Se o nó direito for nulo, retornará o esquerdo
                return no.esquerda;
            }

            no.info = maiorValor(no.esquerda); // Utiliza uma das regras possíveis para remover elementos da árvore, "utilizar o maior elemento da subárvore esquerda para 'substituir' o elemento a ser retirado"
            no.esquerda = removerElemento(no.esquerda, no.info); // Remove o elemento à esquerda
        }

        return no;
    }

    private int maiorValor(Node no) { // Função que procura o maior valor
        int max = no.info;
        while (no.direita != null) {
            max = no.direita.info;
            no = no.direita;
        }
        return max;
    }

    public boolean buscar(int valor) { // Função de busca
        Node atual = raiz;
        while (atual != null) { // Enquanto o nó atual não for vazio, caso o valor seja o procurado, retornará que o valor foi encontrado
            if (valor == atual.info) {
                return true;
            } 
            
            else if (valor < atual.info) { // Caso o valor buscado seja menor que o nó atual, o nó atual receberá o valor do nó esquerdo
                atual = atual.esquerda;
            } 
            
            else { // Caso for maior, receberá o valor do nó direito
                atual = atual.direita;
            }
        }
        return false;
    }

    public void imprimirArvore() { // Função para imprimir a árvore
        imprimirArvoreRecursivo(raiz);
        System.out.println();
    }

    private void imprimirArvoreRecursivo(Node no) { // Imprime a árvore Inordem
        if (no != null) {
            imprimirArvoreRecursivo(no.esquerda);
            System.out.print(no.info + " ");
            imprimirArvoreRecursivo(no.direita);
        }
    }

    public void imprimirPreOrdem() {
        imprimirPreOrdemRecursivo(raiz);
        System.out.println(); 
    }

    private void imprimirPreOrdemRecursivo(Node no) { // Imprime a árvore Preordem
        if (no != null) {
            System.out.print(no.info + " "); 
            imprimirPreOrdemRecursivo(no.esquerda);
            imprimirPreOrdemRecursivo(no.direita);
        }
    }

    public void imprimirPosOrdem() {
        imprimirPosOrdemRecursivo(raiz);
        System.out.println();
    }

    private void imprimirPosOrdemRecursivo(Node no) { // Imprime a árvore Pósordem
        if (no != null) {
            imprimirPosOrdemRecursivo(no.esquerda);
            imprimirPosOrdemRecursivo(no.direita);
            System.out.print(no.info + " "); 
        }
    }
}

public class Main { // Classe principal
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Variável para leitura do teclado
        ArvoreBinaria arvore = new ArvoreBinaria(); // Instanciamento da árvore

        boolean sair = false; // Variável para manter o loop enquanto for desejo do usuário
        while (!sair) {
            System.out.println("Menu:"); // Mostra as opções para o usuário seguir
            System.out.println("1. Inserir elementos aleatórios");
            System.out.println("2. Buscar elemento na árvore");
            System.out.println("3. Remover elemento da árvore");
            System.out.println("4. Imprimir árvore");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt(); // Lê o teclado do usuário

            switch (opcao) {
                case 1: // Insere a quantidade de elementos desejada pelo usuário
                    System.out.print("Digite a quantidade de elementos a serem inseridos na árvore: ");
                    int quantidade = scanner.nextInt();
                    arvore.inserirValoresGerados(quantidade);
                    break;
                case 2: // Procura o valor desejado
                    System.out.print("Digite o valor a ser buscado na árvore: ");
                    int buscado = scanner.nextInt();
                    boolean encontrado = arvore.buscar(buscado);

                    if (encontrado) {
                        System.out.println("Valor " + buscado + " encontrado na árvore.");
                    } 
                    
                    else {
                        System.out.println("Valor " + buscado + " não encontrado na árvore.");
                    }

                    break;
                case 3: // Remove o valor desejado
                    System.out.print("Digite o valor a ser removido da árvore: ");
                    int excluido = scanner.nextInt();
                    arvore.remover(excluido);
                    boolean removido = arvore.buscar(excluido);

                    if (!removido) {
                        System.out.println("Valor " + excluido + " removido com sucesso.");
                    } 
                    
                    else {
                        System.out.println("Valor " + excluido + " não foi encontrado.");
                    }

                    break;
                case 4: // Imprime a árvore da maneira desejada
                    System.out.println("Digite como a impressão deve ser feita:");
                    System.out.println("1. Pré-ordem");
                    System.out.println("2. In-ordem");
                    System.out.println("3. Pós-ordem");
                    System.out.print("Escolha uma opção de impressão: ");
                    int opcaoImpressao = scanner.nextInt();
                    switch (opcaoImpressao) {
                        case 1:
                            System.out.print("Pré-ordem: ");
                            arvore.imprimirPreOrdem();
                            break;
                        case 2:
                            System.out.print("In-ordem: ");
                            arvore.imprimirArvore();
                            break;
                        case 3:
                            System.out.print("Pós-ordem: ");
                            arvore.imprimirPosOrdem();
                            break;
                        default:
                            System.out.println("Impressão inválida.");
                    }
                    break;
                case 5: // Finaliza o programa
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close(); // Evita novas entradas do teclado
    }
}