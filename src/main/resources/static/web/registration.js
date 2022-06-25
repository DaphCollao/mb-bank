Vue.createApp({
    data() {
        return {
            registrationUrlApi:"http://localhost:8080/api/registrationConfirm/",
            token:"",
        }
    },
    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const paramToken = urlParams.get('token'); 
        axios.get(this.registrationUrlApi+`${paramToken}`)
            .then(datos =>{
                console.log("ay por favo que funcione")
            })
    },
    methods: {

    },

    computed: {
        
    }
}).mount('#app')