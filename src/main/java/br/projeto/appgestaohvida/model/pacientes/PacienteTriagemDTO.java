package br.projeto.appgestaohvida.model.pacientes;

public class PacienteTriagemDTO{
        private String nome_completo;
        private String senha;
        private String data_nascimento;
        private String CPF;
        private String tipo_de_atendimento;
        private String telefone;
        private String email;
        private String genero;
        private String rua;
        private String cep;

        // Construtor padrão
        public PacienteTriagemDTO() {
        }

        // Construtor com todos os parâmetros
        public PacienteTriagemDTO(String nomeCompleto, String senha, String dataNascimento, String cpf,
                           String tipoDeAtendimento, String telefone, String email, String genero,
                           String rua, String cep) {
            this.nome_completo = nomeCompleto;
            this.senha = senha;
            this.data_nascimento = dataNascimento;
            this.CPF = cpf;
            this.tipo_de_atendimento = tipoDeAtendimento;
            this.telefone = telefone;
            this.email = email;
            this.genero = genero;
            this.rua = rua;
            this.cep = cep;
        }

        // Getters and Setters
        public String getNome_completo() { return nome_completo; }
        public void setNome_completo(String nome_completo) { this.nome_completo = nome_completo; }

        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }

        public String getData_nascimento() { return data_nascimento; }
        public void setData_nascimento(String data_nascimento) { this.data_nascimento = data_nascimento; }

        public String getCPF() { return CPF; }
        public void setCPF(String CPF) { this.CPF = CPF; }

        public String getTipo_de_atendimento() { return tipo_de_atendimento; }
        public void setTipo_de_atendimento(String tipo_de_atendimento) { this.tipo_de_atendimento = tipo_de_atendimento; }

        public String getTelefone() { return telefone; }
        public void setTelefone(String telefone) { this.telefone = telefone; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getGenero() { return genero; }
        public void setGenero(String genero) { this.genero = genero; }

        public String getRua() { return rua; }
        public void setRua(String rua) { this.rua = rua; }

        public String getCep() { return cep; }
        public void setCep(String cep) { this.cep = cep; }

        // toString method for easy output
        @Override
        public String toString() {
            return "PacienteDTO{" +
                    "nomeCompleto='" + nome_completo + '\'' +
                    ", senha='" + senha + '\'' +
                    ", dataNascimento='" + data_nascimento + '\'' +
                    ", cpf='" + CPF + '\'' +
                    ", tipoDeAtendimento='" + tipo_de_atendimento + '\'' +
                    ", telefone='" + telefone + '\'' +
                    ", email='" + email + '\'' +
                    ", genero='" + genero + '\'' +
                    ", rua='" + rua + '\'' +
                    ", cep='" + cep + '\'' +
                    '}';
        }
    }


