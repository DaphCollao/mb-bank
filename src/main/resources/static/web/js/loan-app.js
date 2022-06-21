window.addEventListener("load", function () {
    const loaderClass = document.getElementById("loader")
    loaderClass.classList.toggle("loader2")
}),

    Vue.createApp({
        data() {
            return {
                // API URL
                clientApiUrl: '/api/clients/current',
                loanApiUrl: '/api/loans',
                loanApplicationApiUrl: '/api/loans',

                // Arrays with general info
                clientInfo: [],
                accounts: [],
                accountsEnable:[],
                loans: [],

                // Array with loans info
                loanPayments: [],
                mortgageLoan: [],
                personalLoan: [],
                autoLoan: [],
                mortgage: [],
                personal: [],
                auto: [],

                // Client Loans
                clientMortgage: [],
                clientPersonal: [],
                clientAuto: [],

                // V-Model
                loanId: "",
                loanAmount: "",
                loanPayment: "selectPayment",
                loanAccount: "selectAccount",

                // V-Show
                showForm: false,
            }
        },
        created() {
            axios.get(this.clientApiUrl)
                .then(datos => {
                    this.clientInfo = datos.data
                    this.accounts = this.clientInfo.accounts
                    this.accountsEnable = this.accounts.filter(account => account.enable == true)
                    this.clientLoan = this.clientInfo.loans
                    console.log(this.clientLoan.length)

                    this.clientMortgage = this.clientLoan.filter(loan => loan.name == "mortgage")
                    this.clientPersonal = this.clientLoan.filter(loan => loan.name == "personal")
                    this.clientAuto = this.clientLoan.filter(loan => loan.name == "auto")
                })
                .catch(error => console.log(error))


            axios.get(this.loanApiUrl)
                .then(datos => {
                    this.loans = datos.data

                    this.mortgageLoan = this.loans.filter(loan => loan.id == 1)
                    this.mortgage = this.mortgageLoan[0]

                    this.personalLoan = this.loans.filter(loan => loan.id == 2)
                    this.personal = this.personalLoan[0]

                    this.autoLoan = this.loans.filter(loan => loan.id == 3)
                    this.auto = this.autoLoan[0]
                })
                .catch(error => console.log(error))
        },
        methods: {
            logOutFunc() {
                axios.post('/api/logout')
                    .then(response => {
                        console.log('You are signed out')
                        window.location.href = "/web/index.html"
                    })
            },
            showBtnFunc() {
                this.showForm = true;
                window.setTimeout(function () { scrollTo(0, document.body.scrollHeight) }, 100)
            },
            applyLoanFunc() {
                axios.post(this.loanApplicationApiUrl,
                    {
                        "loanId": this.loanId,
                        "amountLoan": this.loanAmount,
                        "loanPayment": this.loanPayment,
                        "loanAccountNumber": this.loanAccount
                    })
                    .then(response => {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Loan applied successfully',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        if (this.clientLoan.length < 2){
                            window.setTimeout(function () { location.reload() }, 2000)
                        } else {
                            window.location.href = "/web/pages/accounts.html"
                        }

                    })
                    .catch(error => {
                        let err = error.response.data
                        if (err == "Missing data") {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Error in Transaction',
                                html: err,
                                showConfirmButton: false,
                                timer: 2000
                            })
                        } else if (err == "Loan amount exceeds max amount") {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Error in Transaction',
                                html: err,
                                showConfirmButton: false,
                                timer: 2000
                            })
                        } else if (err == "Already have mortgage loan") {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Error in Transaction',
                                html: err,
                                showConfirmButton: false,
                                timer: 2000
                            })
                        } else if (err == "Already have personal loan") {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Error in Transaction',
                                html: err,
                                showConfirmButton: false,
                                timer: 2000
                            })
                        } else if (err == "Already have auto loan") {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Error in Transaction',
                                html: err,
                                showConfirmButton: false,
                                timer: 2000
                            })
                        }
                    })
            }
        },

        computed: {
        }
    }).mount('#app')
