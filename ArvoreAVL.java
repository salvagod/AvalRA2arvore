import java.util.Random;
import java.util.Scanner;

class Node { // Classe Node
    int info;
    Node esquerda;
    Node direita;
    int altura;

    public Node(int info) {
        this.info = info;
        this.esquerda = null;
        this.direita = null;
        this.altura = 1;
    }
}

class ArvoreAVL2 { // Classe Árvore AVL
    Node raiz; // Inicialização da raiz

    public ArvoreAVL2() { // Inicialização da classe, raiz sendo vazia
        this.raiz = null;
    }

    private Node inserirAVL(Node no, int valor) { // Método de inserção
        if (raiz == null) { // Se a raiz for nula, retorna o primeiro valor inserido
            return new Node(valor);
        }

        if (valor < no.info) {  // Se o valor for menor que o valor do nó atual, fará a inserção pelo lado esquerdo
            no.esquerda = inserirAVL(no.esquerda, valor);
        } else if (valor > no.info) { // Senão fará pelo lado direito
            no.direita = inserirAVL(no.direita, valor);
        }
        
        no.altura = 1 + maximoAltura(no.esquerda, no.direita); // Atualiza a altura do nó atual

        
        int balanceamento = obterBalanceamento(no); // Verifica o balanceamento do nó atual

        if (balanceamento > 1 && valor < no.esquerda.info) { // Desequilíbrio na esquerda
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor > no.direita.info) { // Desequilíbrio no lado direito
            return rotacaoEsquerda(no);
        }

        if (balanceamento > 1 && valor > no.esquerda.info) { // Desequilibrio da esquerda para a direita
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && valor < no.direita.info) { // Desequilíbrio da direita para a esquerda
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private Node menorValor(Node no) { // Procura o menor valor no nó recebido
        Node atual = no;

        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }

    private int maximoAltura(Node esquerda, Node direita) { // Função que vê a maior altura entre o nó esquerdo e direito
        int alturaEsquerda = altura(esquerda);
        int alturaDireita = altura(direita);
        
        if (alturaEsquerda > alturaDireita) {
            return alturaEsquerda;
        } else {
            return alturaDireita;
        }
    }

    public void remover(int valor) { // Método de remoção
        raiz = removerAVL(raiz, valor);
    }

    private Node removerAVL(Node no, int valor) {
        if (no == null) { // Se o nó estiver vazio, retorna o próprio nó
            return no;
        }

        if (valor < no.info) { // Se o valor for menor que o valor do nó atual, continuará a busca pelo lado esquerdo
            no.esquerda = removerAVL(no.esquerda, valor);
        } else if (valor > no.info) { // Se o valor for maior que o valor do nó atual, continuará a busca pelo lado direito
            no.direita = removerAVL(no.direita, valor);
        } else { // Caso o valor seja encontrado
            if (no.esquerda == null || no.direita == null) { // Se possuir um ou nenhum nó filho, uma variável temporária receberá o valor do nó a esquerda, caso ele não seja nulo, senão receberá o da direita
                Node temp = (no.esquerda != null) ? no.esquerda : no.direita;

                if (temp == null) { // Caso não haja nós filhos, o valor temporário recebe o valor do nó, e nó recebe um valor vazio
                    temp = no;
                    no = null;
                } else { // Caso haja um nó filho, nó recebe o valor temporário
                    no = temp;
                }
            } else { // Caso haja 2 nós filhos, procurará o menor valor do nó
                Node temp = menorValor(no.direita); // O valor temporário receberá o menor valor do nó direito

                no.info = temp.info; // Os valores do nó serão recebidos pelos valores temporários

                no.direita = removerAVL(no.direita, temp.info); // O nó da direita será removido
            }
        }
        
        if (no == null) { // Verifica se o nó é vazio ou não
            return no;
        }

        no.altura = 1 + maximoAltura(no.esquerda, no.direita); // Atualiza a altura do nó atual
 
        int balanceamento = obterBalanceamento(no); // Verifica o balanceamento do nó atual

        if (balanceamento > 1 && obterBalanceamento(no.esquerda) >= 0) { // Desequilibrio no lado esquerdo
            return rotacaoDireita(no);
        }
        
        if (balanceamento < -1 && obterBalanceamento(no.direita) <= 0) { // Desequilíbrio no lado direito
            return rotacaoEsquerda(no);
        }
        
        if (balanceamento > 1 && obterBalanceamento(no.esquerda) < 0) { // Desequilibrio da esquerda para a direita
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && obterBalanceamento(no.direita) > 0) { // Desequilíbrio da direita para a esquerda
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private int obterBalanceamento(Node no) { // Recebe o valor do fator de balanceamento
        if (no == null) {
            return 0;
        }
        return altura(no.esquerda) - altura(no.direita);
    }

    private Node rotacaoDireita(Node no) {
        Node novaRaiz = no.esquerda; // A nova raiz recebe o valor do nó esquerdo
        Node temp = novaRaiz.direita; // A variável temporária recebe o nó direito da nova raiz

        
        novaRaiz.direita = no; // Faz a rotação
        no.esquerda = temp; // O nó esquerdo recebe o valor temporário

        no.altura = 1 + altura(no); // Atualiza as alturas
        novaRaiz.altura = 1 + altura(novaRaiz);

        
        return novaRaiz; // Retorna a nova raiz
    }

    
    private Node rotacaoEsquerda(Node no) { // Rotação para a esquerda
        Node novaRaiz = no.direita; // A nova raiz recebe o valor do nó direito
        Node temp = novaRaiz.esquerda;  // A variável temporário recebe o nó esquerdo da nova raiz
        
        novaRaiz.esquerda = no; // Faz a rotação
        no.direita = temp; // O nó direito recebe o valor temporário

        no.altura = 1 + altura(no); // Atualiza as alturas
        novaRaiz.altura = 1 + altura(novaRaiz);

        
        return novaRaiz; // Retorna a nova raiz
    }

    
    private int altura(Node no) { // Calcula a altura do nó
        if (no == null) {
            return -1;
        }
        return altura(no.esquerda) > altura(no.direita) ? 1 + altura(no.esquerda) : 1 + altura(no.direita); // Caso o nó esquerdo for mais alto, retorna a altura do nó esquerdo + 1, senão, retorna altura do nó direito + 1
    }

    public void inserirValores(int quantidade) { // Método para gerar os valores aleatório e inserí-los na árvore
        Random random = new Random();

        for (int i = 0; i < quantidade; i++) {
            int valor = random.nextInt(100); // Gera valores aleatórios entre 0 e 99
            raiz = inserirAVL(raiz, valor);
        }
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

    public void imprimirArvore() {
        imprimirArvoreRecursivo(raiz);
        System.out.println();
    }

    private void imprimirArvoreRecursivo(Node no) { //Imprime árvore no modo inordem
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

    private void imprimirPreOrdemRecursivo(Node no) { // Imprime árvore no modo préordem
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

    private void imprimirPosOrdemRecursivo(Node no) { // Imprime árvore no modo pósordem
        if (no != null) {
            imprimirPosOrdemRecursivo(no.esquerda);
            imprimirPosOrdemRecursivo(no.direita);
            System.out.print(no.info + " ");
        }
    }
}

public class ArvoreAVL {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Variável para leitura do teclado
        ArvoreAVL2 arvore = new ArvoreAVL2(); // Instanciamento da classe arvore

        boolean sair = false; // Variável para manter loop
        while (!sair) {
            System.out.println("Menu:"); // Menu para mostrar opções
            System.out.println("1. Inserir elementos aleatórios");
            System.out.println("2. Buscar elemento na árvore");
            System.out.println("3. Remover elemento da árvore");
            System.out.println("4. Imprimir árvore");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt(); // Lê opção do usuário

            switch (opcao) {
                case 1: // Insere a quantidade de elementos desejado pelo usuário
                    System.out.print("Digite a quantidade de elementos a serem inseridos na árvore: ");
                    int quantidade = scanner.nextInt();
                    arvore.inserirValores(quantidade);
                    break;
                case 2: // Busca o elemento desejado
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
                case 3: // Remove o elemento desejado
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
                case 4: // Faz a impressão do modo desejado
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
                case 5: // Finaliza programa
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close(); // Evita novas entradas do teclado
    }
}