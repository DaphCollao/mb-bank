Vue.createApp({
    data() {
        return {
            clientApiUrl:'http://localhost:8080/api/clients/current',
            admin:[],
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
    },

    computed: {
    }
}).mount('#app')
