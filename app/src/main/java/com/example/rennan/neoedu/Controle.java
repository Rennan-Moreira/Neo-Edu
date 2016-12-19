package com.example.rennan.neoedu;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by André on 24/11/2016.
 * Modified by Rennan on 30/12/2016.
 */

public  class Controle {
    private static ArrayList<Aluno> alunos;
    private static ArrayList<Professor> professores;
    private static ArrayList<Pergunta> perguntas;

    public Controle(){
        alunos = new ArrayList<Aluno>();
        professores = new ArrayList<Professor>();
        perguntas = new ArrayList<Pergunta>();

        criaPerguntas();
    }

    private void criaPerguntas() {
        //0 a 9 - Banco de Dados
        perguntas.add(new Pergunta("Banco de dados é considerado um conjunto de:","Dados e informações","Dados relacionados","Dados não relacionados","Informações relacionadas","Informações não relacionadas", 2));
        perguntas.add(new Pergunta("O que significam as letras S e G de SGBD?","Sistema Gerador","Software Gerador","Sistema Gerenciador", "Software Gerenciador","Software Generalizador",3));
        perguntas.add(new Pergunta("Quais são os principais componentes de um sistema de banco de dados?","Dados e informações","Hardware e software","Dados, hardware e software","Informações, hardware e software","Dados, hardware, software e usuários",5));
        perguntas.add(new Pergunta("Os dados de um banco de dados sempre são:","Persistentes, integrados e compartilhados","Persistentes, desassociados e compartilhados","Temporários, integrados e compartilhados","Temporários, desassociados e compartilhados", "Temporários, desassociados e particulares",1));
        perguntas.add(new Pergunta("Quais desses 4 modelos de banco de dados realmente existem?","Rede, Relacional, Sequencial e Orientado a Objetos","Rede, Relacional, Hieráquico e Orientado a Objetos","Rede, Relacional, Hieráquico e Sequencial","Sequencial, Relacional, Hieráquico e Orientado a Objetos", "Rede, Sequencial, Hieráquico e Orientado a Objetos",2));
        perguntas.add(new Pergunta("Qual a sequencia de modelagem de dados deve ser seguida?","Físico, Conceitual e Lógico","Físico, Lógico e Conceitual","Conceitual, Físico e Lógico","Conceitual, Lógico e Físico", "Lógico, Físico e Conceitual",4));
        perguntas.add(new Pergunta("O modelo Entidade Relacionamento (MER) é um modelo de dados:","Físico de baixo nível","Lógico de baixo nível","Lógico de alto nível","Conceitual de baixo nível", "Conceitual de alto nível",5));
        perguntas.add(new Pergunta("No modelo Entidade Relacionamento, o que é um Relacionamento?","É uma ligação entre apenas dois atributos","É uma ligação entre dois ou mais atributos","É a ligação entre apenas duas entidades","É a ligação entre mais de duas entidades","É a ligação entre duas ou mais entidades",5));
        perguntas.add(new Pergunta("A cardinalidade no Banco de Dados é:","Um atributo que não pode ser repetido","O grau de ligação entre as entidades","O atributo que identifica um registro na tabela", "Uma informação que caracteriza um atributo","Uma informação que caracteriza uma entidade",2));
        perguntas.add(new Pergunta("Auto-Relacionamento são relacionamentos:","Entre uma mesma entidade","Entre 2 entidades","Entre 3 ou mais entidades", "Entre entidades distintas","Entre 2 ou mais atributos",1));
    }

    public String[] getPergunta(int pos){
        String[] a = {"","","","","",""};
        a[0] = perguntas.get(pos).getEnunciado();
        a[1] = perguntas.get(pos).getLA();
        a[2] = perguntas.get(pos).getLB();
        a[3] = perguntas.get(pos).getLC();
        a[4] = perguntas.get(pos).getLD();
        a[5] = perguntas.get(pos).getLE();

        return a;
    }

    public int getCerto(int pos) {
        return perguntas.get(pos).getCerto();
    }

    public void addAluno(Aluno temp){
        alunos.add(temp);
        //alunos.get(alunos.size()-1).setCodAluno(alunos.size()-1);
    }

    public void addProfessor(Professor temp){
        professores.add(temp);
    }

    public Aluno pesquisarAluno(int codAluno){
        Aluno temp = new Aluno();
        for (Aluno a : alunos) {
            if(codAluno == a.getCodAluno()){
                temp = a;
            }
        }
        return temp;
    }

    public Professor pesquisarProfessor(int codProfessor){
        Professor temp = new Professor();
        for(Professor p : professores){
            if(codProfessor == p.codProfessor){
                temp = p;
            }
        }
        return temp;
    }

    public int getLengthAlunos(){
        return alunos.size();
    }

    public int getLengthProfessores(){
        return professores.size();
    }

    public void setnomeProfessor(int codProfessor, String novoNome){
        pesquisarProfessor(codProfessor).setNome(novoNome);
    }

    public void setnomeAluno(int codAluno, String novoNome){
        pesquisarAluno(codAluno).setNome(novoNome);
    }

    public void setUsuarioProfessor(int codProfessor, String novoUsuario){
        pesquisarProfessor(codProfessor).setUsuario(novoUsuario);
    }

    public void setUsuarioAluno(int codAluno, String novoUsuario) {
        pesquisarAluno(codAluno).setUsuario(novoUsuario);
    }

    public void setSenhaoProfessor(int codProfessor, String novoSenha){
        pesquisarProfessor(codProfessor).setUsuario(novoSenha);
    }

    public void setSenhaAluno(int codAluno, String novoSenha){
        pesquisarAluno(codAluno).setUsuario(novoSenha);
    }

    public void setdtNascAluno(int codAluno, Date dtNasc){
        pesquisarAluno(codAluno).setNasc(dtNasc);
    }

    public void setdtNascProfessor(int codProfessor, Date dtNasc){
        pesquisarProfessor(codProfessor).setNasc(dtNasc);
    }

    public void setEmailAluno(int codAluno, String novoEmail){
        pesquisarAluno(codAluno).setEmail(novoEmail);
    }

    public void setEmailProfessor(int codProfessor, String novoEmail){
        pesquisarProfessor(codProfessor).setEmail(novoEmail);
    }

    public String getNomeAluno(int codAluno){
        return pesquisarAluno(codAluno).getNome();
    }

    public String getNomeProfessor(int codProfessor){
        return pesquisarProfessor(codProfessor).getNome();

    }

    public Date getdtNascAluno(int codAluno){
        return pesquisarAluno(codAluno).getNasc();
    }

    public Date getdtNascProfessor(int codProfessor){
        return pesquisarProfessor(codProfessor).getNasc();
    }

    public String getEmailAluno(int codAluno){
        return pesquisarAluno(codAluno).getEmail();
    }

    public String getEmailProfessor(int codProfessor){
        return pesquisarProfessor(codProfessor).getEmail();
    }

    public String getUsuarioProfessor(int codProfessor){
        return pesquisarProfessor(codProfessor).getUsuario();
    }

    public String getUsuarioAluno(int codAluno) {
        return pesquisarAluno(codAluno).getUsuario();
    }

    public String getSenhaProfessor(int codProfessor){
        return pesquisarProfessor(codProfessor).getSenha();

    }

    public String getSenhaAluno(int codAluno){
        return pesquisarAluno(codAluno).getSenha();
    }


}
