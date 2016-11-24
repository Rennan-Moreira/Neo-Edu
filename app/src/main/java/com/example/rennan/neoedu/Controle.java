package com.example.rennan.neoedu;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by André on 24/11/2016.
 */

public class Controle {
    private ArrayList<Aluno> alunos;
    private ArrayList<Professor> professores;

    public Controle(){
        alunos = new ArrayList<Aluno>();
        professores = new ArrayList<Professor>();
    }

    public void addAluno(Aluno temp){
        alunos.add(temp);
    }

    public void addProfessor(Professor temp){
        professores.add(temp);
    }

    public Aluno pesquisarAluno(int codAluno){
        Aluno temp = new Aluno();
        for (Aluno a : alunos) {
            if(codAluno == a.codAluno){
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

    public void setnomeProfessor(int codProfessor, String novoNome){
        pesquisarProfessor(codProfessor).setNome(novoNome);
    }

    public void setnomeAluno(int codAluno, String novoNome){
        pesquisarAluno(codAluno).setNome(novoNome);
    }

    public void setSobrenomeProfessor(int codProfessor, String novoSobrenome){
        pesquisarProfessor(codProfessor).setSobrenome(novoSobrenome);
    }

    public void setSobrenomeAluno(int codAluno, String novoSobrenome){
        pesquisarAluno(codAluno).setSobrenome(novoSobrenome);
    }

    public void setdtNascAluno(int codAluno, Date dtNasc){
        pesquisarAluno(codAluno).setDtNasc(dtNasc);
    }

    public void setdtNascProfessor(int codProfessor, Date dtNasc){
        pesquisarProfessor(codProfessor).setDtNasc(dtNasc);
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

    public String getSobrenomeAluno(int codAluno){
        return pesquisarAluno(codAluno).getSobrenome();
    }

    public String getSobrenomeProfessor(int codProfessor){
        return pesquisarAluno(codProfessor).getSobrenome();
    }

    public Date getdtNascAluno(int codAluno){
        return pesquisarAluno(codAluno).getDtNasc();
    }

    public Date getdtNascProfessor(int codProfessor){
        return pesquisarProfessor(codProfessor).getDtNasc();
    }

    public String getEmailAluno(int codAluno){
        return pesquisarAluno(codAluno).getEmail();
    }

    public String getEmailProfessor(int codProfessor){
        return pesquisarProfessor(codProfessor).getEmail();
    }
}
