Vue.createApp({
    data() {
        return {
            clientApiUrl:'http://localhost:8080/api/clients/current',
            client:[],
            cards:[],
            debitCards:[],
            creditCards:[],

            //Show-Hide
            showLoans: false,
            logged: false,
        }
    },
    created() {
        this.isAuthenticatedFunc()
    },
    methods: {
        logOutFunc(){
            axios.post('/api/logout')
                .then(response => {
                    console.log('You are signed out')
                    window.location.reload
                })
        },
        isAuthenticatedFunc(){
            axios.get('/api/authenticated')
                .then(response => {
                    if (response.data == 'Authenticated'){
                        this.logged = true
                        axios.get(this.clientApiUrl)
                        .then(datos => {
                            this.client = datos.data
                        })
                        .catch(error => console.log(error))
                    } else {
                        this.logged = false
                        console.log('Not logged')
                    }
                })
        }
    },
    
    computed: {
    }
}).mount('#app')
