import { defineStore } from 'pinia'
export const useStore = defineStore('id', {
  state: () => {
    return {
      token: '',
    }
  },
  actions: {
    SetToken(value: string) {
      this.token = value
    },
  },
})