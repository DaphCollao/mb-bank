Vue.createApp({
    data() {
        return {
            postApi:'http://localhost:8080/api/payment',
            //V-model
            cardNumber:"",
            cardPaymentDescription:"", 
            cardCvv:"",
            cardFirstName:"",
            cardLasttName:"",
            paymentAmount:500,
        }
    },
    created() {
        document.querySelector("#amount").textContent()
    },
        
    methods: {
        paymentFunc(){
            axios.post(this.clientApiUrl,
                {
                    "cardNumber": this.cardNumber,
                    "cardPaymentDescription": this.cardPaymentDescription,
                    "cardCvv": this.cardCvv,
                    "paymentAmount":this.paymentAmount,
                })
                .then(datos => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Payment successful',
                        showConfirmButton: false,
                        timer: 1500
                    })
                })
            .catch(error => console.log(error))
        }
    },

    computed: {
    }
}).mount('#app')