window.addEventListener("load" , function(){
    const loaderClass = document.getElementById("loader")
    loaderClass.classList.toggle("loader2")
}),

Vue.createApp({
    data() {
        return {
            clientApiUrl:'/api/clients/current',
            accountApiUrl:'/api/clients/current/accounts',
            client:[],
            accounts:[],
            accountsEnable:[],
            transactions:[],
            loans:[],

            // V-model
            accountType:"",

            //Show-Hide
            showLoans: false,
        }
    },
    created() {
        axios.get(this.clientApiUrl)
            .then(datos => {
                this.client = datos.data
                this.accounts = this.client.accounts
                this.accountsEnable = this.accounts.filter(account => account.enable == true)
                this.loans = this.client.loans
            })
            .catch(function(error) {
                if (error.response && error.response.status === 401) {
                    window.location.href = "/web/index.html"
                }
            })

    },
    methods: {
        formatDate(date){
            let newDate = new Date(date)
            let day = newDate.getDate().toString().padStart(2,'0')
            let month = (newDate.getMonth()+1).toString().padStart(2,'0')
            let year = newDate.getFullYear().toString()
            let monthYear = day+'/'+month+'/'+year
            return monthYear
        },

        logOutFunc(){
            axios.post('/api/logout')
                .then(response => {
                    console.log('You are signed out')
                    window.location.href = "/web/index.html"
                })
        },
        
        createNewAccountFunc(){
            axios.post(this.accountApiUrl,
                `accountType=${this.accountType}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    window.location.reload()
                })
        }
    },
    
    computed: {
        transactionsList(){
            this.accounts.forEach(account => {
                account.transactions.forEach(trans =>{
                    this.transactions.push(trans)
                    this.transactions.sort((a,b) => b.id - a.id)
                    this.transactions = this.transactions.slice(0,10)
                })
            })
        },
    }
}).mount('#app')
