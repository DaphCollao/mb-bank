Vue.createApp({
    data() {
        return {
            //Api
            loansApiUrl: '/api/loans',
            loans:[],

            //Sign In
            logInPath:'/api/login',
            email: '',
            password: '',

            //Error
            errorCatch: false, 

            //Sign Up
            singUpPath:'/api/clients',
            newFirstName:"",
            newLastName:"",
            newEmail:"",
            newPassword:"",

            //Change button SingUp/In
            changeSingUp: false,
        }
    },
    created() {
        axios.get(this.loansApiUrl)
            .then(datos =>{
                this.loans = datos.data
            })
    },
    methods: {
        logInFunc() {
            axios.post(this.logInPath, 
                    `email=${this.email}&password=${this.password}`,
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    console.log("Youre sign in")
                    if (this.email.includes("@mbb-admin.com")){
                        window.location.href = "http://localhost:8080/web/manager/manager.html"
                    } else {
                        window.location.href = "http://localhost:8080/web/pages/accounts.html"
                    }

                })
                .catch(error => 
                    this.errorCatch = true,
                )
        },

        singUpFunc(){
            axios.post(this.singUpPath, 
                `firstName=${this.newFirstName}&lastName=${this.newLastName}&email=${this.newEmail}&password=${this.newPassword}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    console.log('registered')
                    axios.post(this.logInPath, 
                        `email=${this.newEmail}&password=${this.newPassword}`,
                        {headers:{'content-type':'application/x-www-form-urlencoded'}})
                        .then(response => {
                            console.log("Youre sign in")
                            window.location.href = "http://localhost:8080/web/pages/accounts.html"
                        })
                })
                .catch(error => {
                    const typeOfError = error.response.data
                    let timerInterval
                    if (typeOfError == "Missing data"){
                        Swal.fire({
                            title: "There's been an error",
                            html: 'All fields are required',
                            timer: 3000,
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading()
                                const b = Swal.getHtmlContainer().querySelector('b')
                                timerInterval = setInterval(() => {
                                    b.textContent = Swal.getTimerLeft()
                                }, 100)
                            },
                            willClose: () => {
                                clearInterval(timerInterval)
                            }
                        })
                    } else {
                        Swal.fire({
                            title: "There's been an error",
                            html: 'Email already in use',
                            timer: 3000,
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading()
                                const b = Swal.getHtmlContainer().querySelector('b')
                                timerInterval = setInterval(() => {
                                    b.textContent = Swal.getTimerLeft()
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