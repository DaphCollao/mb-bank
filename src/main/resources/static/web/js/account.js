window.addEventListener("load" , function(){
    const loaderClass = document.getElementById("loader")
    loaderClass.classList.toggle("loader2")
}),

Vue.createApp({
    data() {
        return {
            clientApiUrl: 'http://localhost:8080/api/clients/current',
            accountUrl: 'http://localhost:8080/api/accounts/',
            disableAccountUrl: 'http://localhost:8080/api/clients/current/accounts',
            clientInfo:[],
            account:[],
            transactions: [],
            totaltransactions: [],
            totalCredit: 1,
            totalDebit: 1,
        }
    },
    created() {
        //Axios gets Client Info
        axios.get(this.clientApiUrl)
            .then(datos => {
                this.clientInfo = datos.data
            })

        //Axios gets account info
        const urlParams = new URLSearchParams(window.location.search);
        const paramId = urlParams.get('id'); 
        axios.get(this.accountUrl+`${paramId}`)
            .then(datos => {
                this.account = datos.data
                this.transactions = this.account.transactions
                this.totaltransactions
                console.log(this.account)
            })
    },
    methods: {        
        formatDate(date){
            let newDate = new Date(date)
            let day = newDate.getDate().toString().padStart(2,'0')
            let month = (newDate.getMonth()+1).toString().padStart(2,'0')
            let year = newDate.getFullYear().toString()
            let hour = newDate.getHours().toString().padStart(2,'0')
            let minutes = newDate.getMinutes().toString().padStart(2,'0')
            let monthYear = day+'/'+month+'/'+year+' '+hour+':'+minutes
            return monthYear
        },
        
        logOutFunc(){
            axios.post('/api/logout')
                .then(response => {
                    console.log('You are signed out')
                    window.location.href = "http://localhost:8080/web/index.html"
                })
        },
        disableAccountFunc(idPath){
            axios.patch(this.disableAccountUrl, 
                `id=${idPath}`, 
                { headers: { 'content-type': 'application/x-www-form-urlencoded' }})
                .then(response => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Account disable',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    window.setTimeout(function () { window.location.href = "http://localhost:8080/web/pages/accounts.html"
                }, 2000)
                })
                .catch(error => console.log(error))
        }
    },
    
    computed: {
        orderTransactionsFunc(){
            this.transactions.sort((a,b) => b.id - a.id)
        },
    }
}).mount('#app')