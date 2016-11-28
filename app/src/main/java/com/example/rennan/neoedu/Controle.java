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
