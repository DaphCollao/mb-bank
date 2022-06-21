window.addEventListener("load" , function(){
    const loaderClass = document.getElementById("loader")
    loaderClass.classList.toggle("loader2")
}),

Vue.createApp({
    data() {
        return {
            clientApiUrl:'http://localhost:8080/api/clients/current',
            cardApiUrl: 'http://localhost:8080/api/clients/current/cards',
            client:[],
            cardType: "selectType",
            cardColor: "selectColor",

            cards:[],
            debitCards:[],
            creditCards:[],
            debitColor:[],
            creditColor:[],


            //Show-Hide
            showLoans: false,
        }
    },
    created() {
        axios.get(this.clientApiUrl)
            .then(datos => {
                this.client = datos.data
                this.cards = this.client.cards
                this.debitCards = this.cards.filter(card => card.cardType == 'DEBIT')
                this.creditCards = this.cards.filter(card => card.cardType == 'CREDIT')
                this.creditColor = this.cards.filter(card => card.cardType == 'DEBIT')
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
        
        createNewCardFunc(){
            axios.post(this.cardApiUrl,
                `cardColor=${this.cardColor}&cardType=${this.cardType}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    console.log('New Card created')
                    window.location.href = "http://localhost:8080/web/pages/cards.html"
                })
                .catch(error => {
                    console.log(error.response.data)
                    let err = error.response.data;
                    if (err == `Already have a ${this.cardColor} ${this.cardType} card`){
                        Swal.fire({
                            title: `You already have a ${this.cardColor} ${this.cardType} card`,
                            html: '',
                            timer: 3000,
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading()
                                const b = Swal.getHtmlContainer().querySelector('b')
                                timerInterval = setInterval(() => {
                                    
                                }, 100)
                            },
                            willClose: () => {
                                clearInterval(timerInterval)
                            }
                        })
                    }
                })
        },
    },
    
    computed: {
    }
}).mount('#app')
