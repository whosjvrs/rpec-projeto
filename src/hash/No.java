package hash;

import modelo.Pet;

/**
 Nó da lista ligada usada em cada balde da tabela hash.
 Cada nó guarda um pet e aponta para o próximo nó do mesmo balde.
 Quando "proximo" é null, este é o último nó da lista.
 */
public class No {
    Pet pet;        // o pet guardado neste nó
    No proximo;     // o próximo nó da lista (null se for o último)

    // Cria um nó com o pet; ele nasce sem ninguém depois dele
    public No(Pet pet) {
        this.pet = pet;
        this.proximo = null;
    }
}