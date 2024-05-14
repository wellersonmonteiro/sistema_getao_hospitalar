function formatarHorario(date) {

    const horas = date.getHours().toString().padStart(2, '0');
    const minutos = date.getMinutes().toString().padStart(2, '0');
    const segundos = date.getSeconds().toString().padStart(2, '0');

    return `${horas}:${minutos}:${segundos}`;
}

function atualizarHorario() {
    const elementoHorario = document.getElementById('horarioAtual');

    // Obtenha a data e hora atuais
    const now = new Date();

    // Formate o horário
    const horarioFormatado = formatarHorario(now);

    elementoHorario.textContent = horarioFormatado;
}

setInterval(atualizarHorario, 1000);

function enviarDadosPaciente() {
    const pacienteData = {
        nomeCompleto: document.getElementById("nomeCompleto").value,
        senha: document.getElementById("senha").value,
        viaAerea: document.getElementById("obstrucaoViaAereaCheckbox").checked,
        respiracaoIneficaz: document.getElementById("respiracaoIneficazCheckbox").checked,
        naoRespondeEstimulo: document.getElementById("naoRespondeEstimuloCheckbox").checked,
        emConvulsao: document.getElementById("convulsaoCheckbox").checked,
        dorSevera: document.getElementById("nivelDor").value === "DorSevera",
        grandeHemorragia: document.getElementById("sinaisHemorragia").value === "GrandeHemorragia",
        alteracaoConsciencia: document.getElementById("alteracaoConscienciaCheckbox").checked,
        temperaturaAlta: document.getElementById("temperatura").value === "TemperaturaAlta",
        traumaCraniano: document.getElementById("traumaCranianoCheckbox").checked,
        pequenaHemorragia: document.getElementById("sinaisHemorragia").value === "PequenaHemorragia",
        dorModerada: document.getElementById("nivelDor").value === "DorModerada",
        vomitoPersistente: document.getElementById("VomitoCheckbox").checked,
        temperaturaEntre38e39: document.getElementById("temperatura").value === "TemperaturaEntre38e39",
        idosoOuGravidaSintomatico: document.getElementById("idosoOuGravida").value === "Sim",
        dorLeve: document.getElementById("nivelDor").value === "DorLeve",
        nauseas: document.getElementById("nauseaCheckbox").checked,
        temperaturaEntre37e38: document.getElementById("temperatura").value === "TemperaturaEntre37e38"
    };
    console.log("Dados enviados para o servidor:", pacienteData);

    fetch("http://localhost:8080/consultorio/main", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(pacienteData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao adicionar paciente");
            }
            return response.text();
        })
        .then(data => {
            console.log("Resposta do servidor:", data);
            ativarMain();
        })
        .catch(error => {
            console.error("Erro ao adicionar paciente:", error);
        });
}

async function ativarMain() {
    try {
        const response = await fetch('http://localhost:8080/consultorio/main');
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
        }
        const dados = await response.text();
        console.log('Dados recebidos do endpoint /consultorio/main:', dados);

        await Swal.fire({
            title: 'Operação realizada com sucesso!',
            icon: 'success',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#1c1e1e',
            width: '400px',
            padding: '1rem',

            willClose: () => {
                window.location.href = '../pages/sala-triagem-consultorio.html';
            }
        });

    } catch (error) {
        console.error('Erro ao ativar a função main:', error);
    }
}

document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault();
    enviarDadosPaciente();
});

document.addEventListener("DOMContentLoaded", function() {
    const senha = localStorage.getItem('senha');

    if (senha !== null) {
        const inputCampoSenha = document.getElementById('senha');
        inputCampoSenha.value = senha;
    }

    const nome = localStorage.getItem('nome');

    if (nome !== null) {
        const inputCampoNome = document.getElementById('nomeCompleto');
        inputCampoNome.value = nome;
    }
});