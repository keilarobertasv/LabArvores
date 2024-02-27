package tree;

import estrut.Tree;

public class BinarySearchTree implements Tree {

    private Node raiz;

    private class Node {
        int valor, altura;
        Node esquerda, direita;

        Node(int valor) {
            this.valor = valor;
            this.altura = 1;
        }
    }

    @Override
    public boolean buscaElemento(int valor) {
        return buscaElemento(raiz, valor);
    }

    private boolean buscaElemento(Node node, int valor) {
        if (node == null)
            return false;
        if (valor < node.valor)
            return buscaElemento(node.esquerda, valor);
        else if (valor > node.valor)
            return buscaElemento(node.direita, valor);
        else
            return true;
    }

    @Override
    public int minimo() {
        if (raiz == null)
            return Integer.MIN_VALUE;
        return minimo(raiz).valor;
    }

    private Node minimo(Node node) {
        while (node.esquerda != null)
            node = node.esquerda;
        return node;
    }

    @Override
    public int maximo() {
        if (raiz == null)
            return Integer.MAX_VALUE;
        return maximo(raiz).valor;
    }

    private Node maximo(Node node) {
        while (node.direita != null)
            node = node.direita;
        return node;
    }

    @Override
    public void insereElemento(int valor) {
        raiz = insereElemento(raiz, valor);
    }

    private Node insereElemento(Node node, int valor) {
        if (node == null)
            return new Node(valor);
        if (valor < node.valor)
            node.esquerda = insereElemento(node.esquerda, valor);
        else if (valor > node.valor)
            node.direita = insereElemento(node.direita, valor);
        else
            return node;

        // Atualiza a altura do nó pai
        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));

        // Verifica o balanceamento e realiza as rotações, se necessárias
        int balanceamento = getBalanceamento(node);

        // Caso de rotação simples à direita
        if (balanceamento > 1 && valor < node.esquerda.valor)
            return rotacaoDireita(node);

        // Caso de rotação simples à esquerda
        if (balanceamento < -1 && valor > node.direita.valor)
            return rotacaoEsquerda(node);

        // Caso de rotação dupla à esquerda direita
        if (balanceamento > 1 && valor > node.esquerda.valor) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }

        // Caso de rotação dupla à direita esquerda
        if (balanceamento < -1 && valor < node.direita.valor) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    @Override
    public void remove(int valor) {
        raiz = remove(raiz, valor);
    }

    private Node remove(Node node, int valor) {
        if (node == null)
            return node;

        if (valor < node.valor)
            node.esquerda = remove(node.esquerda, valor);
        else if (valor > node.valor)
            node.direita = remove(node.direita, valor);
        else {
            if (node.esquerda == null || node.direita == null) {
                Node temp = null;
                if (node.esquerda == null)
                    temp = node.direita;
                else
                    temp = node.esquerda;

                if (temp == null) {
                    temp = node;
                    node = null;
                } else
                    node = temp;
            } else {
                Node temp = minimo(node.direita);
                node.valor = temp.valor;
                node.direita = remove(node.direita, temp.valor);
            }
        }

        if (node == null)
            return node;

        // Atualiza a altura do nó pai
        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));

        // Verifica o balanceamento e realiza as rotações, se necessárias
        int balanceamento = getBalanceamento(node);

        // Caso de rotação simples à direita
        if (balanceamento > 1 && getBalanceamento(node.esquerda) >= 0)
            return rotacaoDireita(node);

        // Caso de rotação simples à esquerda
        if (balanceamento < -1 && getBalanceamento(node.direita) <= 0)
            return rotacaoEsquerda(node);

        // Caso de rotação dupla à esquerda direita
        if (balanceamento > 1 && getBalanceamento(node.esquerda) < 0) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }

        // Caso de rotação dupla à direita esquerda
        if (balanceamento < -1 && getBalanceamento(node.direita) > 0) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    // Rotação simples à direita (rotação direita)
    private Node rotacaoDireita(Node y) {
        Node x = y.esquerda;
        Node T2 = x.direita;

        // Realiza a rotação
        x.direita = y;
        y.esquerda = T2;

        // Atualiza as alturas
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    // Rotação simples à esquerda (rotação esquerda)
    private Node rotacaoEsquerda(Node x) {
        Node y = x.direita;
        Node T2 = y.esquerda;

        // Realiza a rotação
        y.esquerda = x;
        x.direita = T2;

        // Atualiza as alturas
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    private int altura(Node node) {
        if (node == null)
            return 0;
        return node.altura;
    }

    private int getBalanceamento(Node node) {
        if (node == null)
            return 0;
        return altura(node.esquerda) - altura(node.direita);
    }

    @Override
    public int[] preOrdem() {
        return preOrdem(raiz);
    }

    private int[] preOrdem(Node node) {
        if (node == null)
            return new int[0];
        int[] resultado = new int[size(node)];
        preOrdem(node, resultado, 0);
        return resultado;
    }

    private int preOrdem(Node node, int[] resultado, int index) {
        if (node == null)
            return index;
        resultado[index++] = node.valor;
        index = preOrdem(node.esquerda, resultado, index);
        index = preOrdem(node.direita, resultado, index);
        return index;
    }

    @Override
    public int[] emOrdem() {
        return emOrdem(raiz);
    }

    private int[] emOrdem(Node node) {
        if (node == null)
            return new int[0];
        int[] resultado = new int[size(node)];
        emOrdem(node, resultado, 0);
        return resultado;
    }

    private int emOrdem(Node node, int[] resultado, int index) {
        if (node == null)
            return index;
        index = emOrdem(node.esquerda, resultado, index);
        resultado[index++] = node.valor;
        index = emOrdem(node.direita, resultado, index);
        return index;
    }

    @Override
    public int[] posOrdem() {
        return posOrdem(raiz);
    }

    private int[] posOrdem(Node node) {
        if (node == null)
            return new int[0];
        int[] resultado = new int[size(node)];
        posOrdem(node, resultado, 0);
        return resultado;
    }

    private int posOrdem(Node node, int[] resultado, int index) {
        if (node == null)
            return index;
        index = posOrdem(node.esquerda, resultado, index);
        index = posOrdem(node.direita, resultado, index);
        resultado[index++] = node.valor;
        return index;
    }

    private int size(Node node) {
        if (node == null)
            return 0;
        return size(node.esquerda) + 1 + size(node.direita);
    }
}