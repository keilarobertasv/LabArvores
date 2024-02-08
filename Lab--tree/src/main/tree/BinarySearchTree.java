package tree;

import estrut.Tree;

public class BinarySearchTree implements Tree {

    private Node root;

    @Override
    public boolean buscaElemento(int valor) {
        return buscaElementoRecursivo(root, valor);
    }

    private boolean buscaElementoRecursivo(Node node, int valor) {
        if (node == null) {
            return false;
        }

        if (valor == node.valor) {
            return true;
        } else if (valor < node.valor) {
            return buscaElementoRecursivo(node.esquerda, valor);
        } else {
            return buscaElementoRecursivo(node.direita, valor);
        }
    }

    @Override
    public int minimo() {
        if (root == null) {
            throw new IllegalStateException("A árvore está vazia.");
        }

        Node nodoMinimo = encontraMinimo(root);
        return nodoMinimo.valor;
    }

    private Node encontraMinimo(Node node) {
        while (node.esquerda != null) {
            node = node.esquerda;
        }
        return node;
    }

    @Override
    public int maximo() {
        if (root == null) {
            throw new IllegalStateException("A árvore está vazia.");
        }

        Node nodoMaximo = encontraMaximo(root);
        return nodoMaximo.valor;
    }

    private Node encontraMaximo(Node node) {
        while (node.direita != null) {
            node = node.direita;
        }
        return node;
    }

    @Override
    public void insereElemento(int valor) {
        root = insereElementoRecursivo(root, valor);
    }

    private Node insereElementoRecursivo(Node node, int valor) {
        if (node == null) {
            return new Node(valor);
        }

        if (valor < node.valor) {
            node.esquerda = insereElementoRecursivo(node.esquerda, valor);
        } else if (valor > node.valor) {
            node.direita = insereElementoRecursivo(node.direita, valor);
        }

        return node;
    }

    @Override
    public void remove(int valor) {
        root = removeRecursivo(root, valor);
    }

    private Node removeRecursivo(Node node, int valor) {
        if (node == null) {
            return null;
        }

        if (valor < node.valor) {
            node.esquerda = removeRecursivo(node.esquerda, valor);
        } else if (valor > node.valor) {
            node.direita = removeRecursivo(node.direita, valor);
        } else {
            // Nodo a ser removido encontrado

            // Caso 1 e 2: Nó com um filho ou sem filhos
            if (node.esquerda == null) {
                return node.direita;
            } else if (node.direita == null) {
                return node.esquerda;
            }

            // Caso 3: Nó com dois filhos
            node.valor = encontraMinimo(node.direita).valor;
            node.direita = removeRecursivo(node.direita, node.valor);
        }

        return node;
    }
// ...

@Override
public int[] preOrdem() {
    return percorrePreOrdem(root);
}

private int[] percorrePreOrdem(Node node) {
    if (node == null) {
        return new int[0];
    }

    int[] left = percorrePreOrdem(node.esquerda);
    int[] right = percorrePreOrdem(node.direita);

    int[] result = new int[left.length + right.length + 1];
    result[0] = node.valor;
    System.arraycopy(left, 0, result, 1, left.length);
    System.arraycopy(right, 0, result, 1 + left.length, right.length);

    return result;
}

@Override
public int[] emOrdem() {
    return percorreEmOrdem(root);
}

private int[] percorreEmOrdem(Node node) {
    if (node == null) {
        return new int[0];
    }

    int[] left = percorreEmOrdem(node.esquerda);
    int[] right = percorreEmOrdem(node.direita);

    int[] result = new int[left.length + 1 + right.length];
    System.arraycopy(left, 0, result, 0, left.length);
    result[left.length] = node.valor;
    System.arraycopy(right, 0, result, left.length + 1, right.length);

    return result;
}

@Override
public int[] posOrdem() {
    return percorrePosOrdem(root);
}

private int[] percorrePosOrdem(Node node) {
    if (node == null) {
        return new int[0];
    }

    int[] left = percorrePosOrdem(node.esquerda);
    int[] right = percorrePosOrdem(node.direita);

    int[] result = new int[left.length + right.length + 1];
    System.arraycopy(left, 0, result, 0, left.length);
    System.arraycopy(right, 0, result, left.length, right.length);
    result[left.length + right.length] = node.valor;

    return result;
}


    private static class Node {
        private int valor;
        private Node esquerda;
        private Node direita;

        public Node(int valor) {
            this.valor = valor;
            this.esquerda = null;
            this.direita = null;
        }
    }
}
