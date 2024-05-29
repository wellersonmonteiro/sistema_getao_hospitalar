function formatarHorario(date) {
    const horas = date.getHours().toString().padStart(2, '0');
    const minutos = date.getMinutes().toString().padStart(2, '0');
    const segundos = date.getSeconds().toString().padStart(2, '0');

    return `${horas}:${minutos}:${segundos}`;
}

function atualizarHorario() {
    const elementoHorario = document.getElementById('horarioAtual');
    const now = new Date();
    const horarioFormatado = formatarHorario(now);

    elementoHorario.textContent = horarioFormatado;
}

setInterval(atualizarHorario, 1000);

document.addEventListener("DOMContentLoaded", function () {
    const senha = localStorage.getItem('senha');

    if (senha !== null) {
        const inputCampo1 = document.getElementById('senha');
        inputCampo1.value = senha;
    }

    const prioridade = localStorage.getItem('Tipo de atendimento');

    if (prioridade !== null) {
        const inputCampo6 = document.getElementById('tipoAtendimento');
        inputCampo6.value = prioridade;
    }
});

function enviarDadosPaciente() {
    const form = document.querySelector('form');
    const dados = {
        senha: form.querySelector('#senha').value,
        nome_completo: form.querySelector('#nomeCompleto').value,
        cpf: form.querySelector('#cpf').value,
        data_nascimento: form.querySelector('#dataNascimento').value,
        genero: form.querySelector('#genero').value,
        tipo_de_atendimento: form.querySelector('#tipoAtendimento').value,
        telefone: form.querySelector('#telefone').value,
        email: form.querySelector('#email').value,
        endereco: form.querySelector('#endereco').value,
        cep: form.querySelector('#cep').value
    };
    console.log('Dados enviados:', dados);

    const url = 'http://localhost:8080/listatriagem/main';
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
    })
        .then(response => {
            if (response.ok) {
                console.log('Dados enviados com sucesso');

                ativarMain();
            } else {
                console.error('Erro ao enviar os dados:', response.status);
            }
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
        });
}

async function ativarMain() {
    try {
        const response = await fetch('http://localhost:8080/listas/main');
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
        }
        const dados = await response.text();
        console.log('Dados recebidos do endpoint /listas/main:', dados);

        await Swal.fire({
            title: 'Operação realizada com sucesso!',
            icon: 'success',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#1c1e1e',
            width: '400px',
            padding: '1rem',

            willClose: () => {
                window.location.href = '../pages/sala-triagem-enfermaria.html';
            }
        });

    } catch (error) {
        console.error('Erro ao ativar a função main:', error);
    }
}

document.querySelector('form').addEventListener('submit', function (event) {
    event.preventDefault();
    enviarDadosPaciente();
});

document.addEventListener('DOMContentLoaded', function() {
    var cpfInput = document.getElementById('cpf');
    if (cpfInput) {
        cpfInput.addEventListener('input', function(event) {
            var value = event.target.value.replace(/\D/g, '');
            if (value.length > 3) {
                value = value.substring(0, 3) + '.' + value.substring(3);
            }
            if (value.length > 7) {
                value = value.substring(0, 7) + '.' + value.substring(7);
            }
            if (value.length > 11) {
                value = value.substring(0, 11) + '-' + value.substring(11);
            }
            if (value.length > 14) {
                value = value.substring(0, 14); // Limita o comprimento máximo do CPF
            }
            event.target.value = value;
        });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    var dataNascimentoInput = document.getElementById('dataNascimento');
    if (dataNascimentoInput) {
        dataNascimentoInput.addEventListener('input', function(event) {
            var value = event.target.value.replace(/\D/g, '');
            if (value.length > 2) {
                value = value.substring(0, 2) + '/' + value.substring(2);
            }
            if (value.length > 5) {
                value = value.substring(0, 5) + '/' + value.substring(5);
            }
            if (value.length > 10) {
                value = value.substring(0, 10); // Limita o comprimento máximo da data de nascimento
            }
            event.target.value = value;
        });
    }
});


document.addEventListener('DOMContentLoaded', function() {
    var telefoneInput = document.getElementById('telefone');
    if (telefoneInput) {
        Inputmask({
            mask: ["(99) 99999-9999", "(99) 9999-9999"],
            keepStatic: true
        }).mask(telefoneInput);
    }
});