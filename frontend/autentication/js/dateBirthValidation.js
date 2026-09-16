

/**
* Captura o evento de input do campo datebirth
*/
const date = document.getElementById("dateBirth");
date.addEventListener("change", function() {
    dateBirthValidation(date);
});

/**
* Verifica se uma data de nascimento passada é valida para registro.
*/
function dateBirthValidation(date) {
  

    // mss em um ano
   let yearMss = 31557600000;

    try {
        // data de nascimento
        let dateBirth = new Date(date.value);

        // data atual
        let actualDate = new Date(Date.now());

        // quantidade de mss percorridos entre uma data de nascimento
        // e a data atual 
        let dateMss = (actualDate.valueOf() - dateBirth.valueOf());

        if (dateMss < 0) {
            throw new Error("Não são aceitas datas do futuro. Por favor, utilize uma data de nascimento valida.");
        }
        else {

            // converte os milesegundos em anos
            let dateYears = Math.floor((actualDate.valueOf() - dateBirth.valueOf()) / yearMss);

            // individuo é menor de idade
            if (dateYears < 18) {
                throw new Error("Não aceitamos usuarios menores de idade.");
            }
            // individuo tem uma idade improvavel
            if (dateYears > 130) {
                throw new Error("Data de nascimento invalida");
            }

        }
    } catch (error) {
        alert(error.message);
        // reseta o valor da data
        let timestamp = new Date(Date.now());
        date.value = "01/01/2001";
    }

}