Vue.createApp({
    data() {
        return {
            registrationUrlApi:"/api/registrationConfirm/",
        }
    },
    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const paramToken = urlParams.get('token'); 
        axios.get(this.registrationUrlApi+`${paramToken}`)
            .then(datos =>{
                console.log("ay po favo que funcione")
            })
            .catch(error => console.log(error))
    },
    methods: {

    },

    computed: {
        
    }
}).mount('#app')