Vue.createApp({
    data() {
        return {
            clientsApiUrl: 'http://localhost:8080/rest/clients',
            clients: [],
            jsonClients: [],

            // V-Model addClient
            clientFirstName: "",
            clientLastName: "",
            clientEmail: "",

            // V-Model Edit Client
            editFirstName: "",
            editLastName: "",
            editEmail: "",

            // Show/hide
            showRest: false,
        }
    },
    created() {
        axios.get(this.clientsApiUrl)
            .then(datos => {
                this.clients = datos.data._embedded.clients
                this.jsonClients = datos.data
            })
    },
        
    methods: {
        addClient() {
            axios.post(this.clientsApiUrl, {
                firstName: this.clientFirstName,
                lastName: this.clientLastName,
                email: this.clientEmail,
            })
                .then(response => {
                    console.log(response),
                    window.location.reload();
                })
                .catch(error => console.log(error))
            },

        removeClient(client){
            axios.delete(client)
            .then(response =>{
                console.log(response);
                window.location.reload()
            })
        },

    },

    computed: {
    }
}).mount('#app')
