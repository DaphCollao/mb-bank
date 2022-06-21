window.addEventListener("load", function () {
    const loaderClass = document.getElementById("loader")
    loaderClass.classList.toggle("loader2")
}),

Vue.createApp({
    data() {
        return {
            clientApiUrl: 'http://localhost:8080/api/clients/current',
            cardApiUrl: 'http://localhost:8080/api/clients/current/cards',
            client: [],
            cards: [],
            debitCards: [],
            creditCards: [],

            //V-model
            cardIdSelector:"",

            // Overdue Card date
            overdueBoo: false,

            //Show-Hide
            showLoans: false,

            // //Active class
            // isActive: false,
        }
    },
    created() {
        axios.get(this.clientApiUrl)
            .then(datos => {
                this.client = datos.data
                this.cards = this.client.cards
                this.debitCards = this.cards.filter(card => card.cardType == 'DEBIT' && card.enable)
                this.creditCards = this.cards.filter(card => card.cardType == 'CREDIT' && card.enable)
            })
    },
    methods: {
            formatDate(date) {
                let newDate = new Date(date)
                let month = (newDate.getMonth() + 1).toString().padStart(2, '0')
                let year = newDate.getFullYear().toString().slice(-2)
                let monthYear = month + '/' + year
                return monthYear
            },

            logOutFunc() {
                axios.post('/api/logout')
                    .then(response => {
                        console.log('You are signed out')
                        window.location.href = "http://localhost:8080/web/index.html"
                    })
            },
            disableCardFunc(cardId) {
                axios.patch(this.cardApiUrl, `id=${cardId}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Card disable',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        window.setTimeout(function () { location.reload() }, 2000)

                    })
                    .catch(error => {
                        let err = error.response.data
                        if (err == "Card doesn't belong to client") {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Error disabling card',
                                html: err,
                                showConfirmButton: false,
                                timer: 2000
                            })
                        } else if (err == "Card doesn't exist") {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Error disabling card',
                                html: err,
                                showConfirmButton: false,
                                timer: 2000
                            })
                        }
                    })
            },
            cardOverdueFunc(cardThruDate) {
                let nowDate = new Date();
                let overdueDate = new Date(cardThruDate);

                if (nowDate > overdueDate) {
                    this.overdueBoo = true
                }
            },
            // addClassActive() {
            //     this.isActive = !this.isActive;
            // },      
    },
        computed: {
            
    }
}).mount('#app')
