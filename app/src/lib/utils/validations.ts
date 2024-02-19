


export const validRegex = {
    username: /^[a-zA-Z0-9_\-\.]{3,20}$/, // a-z A-Z 0-9 _ - . of length 3-20
    name: /^[a-zA-Z0-9\s]{3,20}$/,// a-z A-Z 0-9 of length 3-20
    email: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, // email
    password: /^.{8,255}$/, // all characters of length 8-255

}
