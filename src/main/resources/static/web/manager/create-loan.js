window.addEventListener("load" , function(){
    const loaderClass = document.getElementById("loader")
    loaderClass.classList.toggle("loader2")
}),
Vue.createApp({
    data() {
        return {
            clientApiUrl:'http://localhost:8080/api/clients/current',
            createLoanApiUrl: 'http://localhost:8080/api/createloans',
            admin:[],

            //V-models
            loanName: "",
            loanMaxAmount: "",
            loanFee:"",
            paymentsCheckbox: [],
        }
    },
    created() {
        axios.get(this.clientApiUrl)
            .then(datos => {
                this.admin = datos.data
            })
            .catch(error => console.log(error))
    },
        
    methods: {
        logOutFunc() {
            axios.post('/api/logout')
                .then(response => {
                    console.log('You are signed out')
                    window.location.href = "http://localhost:8080/web/index.html"
                })
        },
        createNewLoanFunc(){
            axios.post(this.createLoanApiUrl,
                `loanName=${this.loanName}&loanMaxAmount=${this.loanMaxAmount}&loanFee=${1+(this.loanFee/100)}&loanPayments=${this.paymentsCheckbox}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    console.log('New Loan created')
                    window.location.reload()
                })
                .catch(error => console.log(error))
        }
    },

    computed: {
    }
}).mount('#app')
