package org.example;

public class Wine {
    private int id;
    private String nome;
    private double prezzo;
    private int tipo;

    public Wine(int id, String name, double prezzo, int tipo) {
        setId(id);
        setNome(nome);
        setPrezzo(prezzo);
        setTipo(tipo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        if (prezzo > 0) {
            this.prezzo = prezzo;
        }
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        if( tipo > 0){
            this.tipo = tipo;
        }
    }
}