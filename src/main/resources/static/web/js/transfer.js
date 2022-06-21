window.addEventListener("load" , function(){
    const loaderClass = document.getElementById("loader")
    loaderClass.classList.toggle("loader2")
}),

Vue.createApp({
    data() {
        return {
            // API URL
            clientApiUrl:'http://localhost:8080/api/clients/current',
            transferApiUrl: 'http://localhost:8080/api/transactions',
            
            // Arrays with info
            clientInfo:[],
            accounts:[],
            accountEnable:[],

            // V-Model
            originNumber: "selectOrigin",
            targetNumber: "",
            targetOption:"",
            amount:"",
            description:"",

            // variables show balance
            accountBalance:"",

            //Error
            errorCatch: false,
        }
    },
    created() {
        axios.get(this.clientApiUrl)
            .then(datos => {
                this.clientInfo = datos.data
                this.accounts = this.clientInfo.accounts
                this.accountsEnable = this.accounts.filter(account => account.enable == true)
            })
            .catch(error => console.log(error))
    },
    methods: {
        logOutFunc(){
            axios.post('/api/logout')
                .then(response => {
                    console.log('You are signed out')
                    window.location.href = "http://localhost:8080/web/index.html"
                })
        },
        resetOptionFunc(){
            this.originNumber = "selectOrigin";
            this.targetNumber = "selectTarget";
        },
        sendTransferFunc(){
            axios.post(this.transferApiUrl,
                `amount=${this.amount}&description=${this.description}&originNumber=${this.originNumber}&targetNumber=${this.targetNumber}`)
                .then(response => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Transaction successful ',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    window.setTimeout(function(){location.reload()}, 2000)
                    
                })
                .catch(error => {
                    let err = error.response.data
                    console.log(err)
                    if (err == "Missing data"){
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Error in Transaction',
                            html: err,
                            showConfirmButton: false,
                            timer: 2000
                        })
                    } else if (err == "Target Account doesn't exist"){
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Error in Transaction',
                            html: err,
                            showConfirmButton: false,
                            timer: 2000
                        })
                    } else if (err == "Transaction amount exceeds account balance"){
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Error in Transaction',
                            html: err,
                            showConfirmButton: false,
                            timer: 2000
                        })
                    }
                })
        },
        showBalanceFunc(accountNumber){
            let accBal;
            accBal = this.accounts.filter(account => account.number == accountNumber)
            this.accountBalance = accBal.map(bal => bal.balance).toString()
            console.log(this.accountBalance)
        }
    },
    
    computed: {
    }
}).mount('#app')
