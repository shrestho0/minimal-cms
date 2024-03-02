type MacroObj = {
    name: string,
    userKey: "name" | "username" | "email",
    macro: string
}

export default [
    { name: 'Name', userKey: "name", macro: '{{name}}' },
    { name: 'Username', userKey: "username", macro: '{{username}}' },
    { name: 'Email', userKey: "email", macro: '{{email}}' }
] as MacroObj[];