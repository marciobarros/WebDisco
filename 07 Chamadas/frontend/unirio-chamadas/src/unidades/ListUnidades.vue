<template>
  <div>
    <v-toolbar flat color="white">
      <v-toolbar-title>My CRUD</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn color="primary" dark class="mb-2">Nova unidade</v-btn>
    </v-toolbar>

    <v-data-table :headers="headers" :pagination.sync="pagination" :items="unidades" :total-items="totalCount" :loading="loading" class="elevation-1">
      <template slot="items" slot-scope="props">
        <tr>
          <td>{{ props.item.sigla }}</td>
          <td>{{ props.item.nome }}</td>
          <td class="justify-center layout px-0">
            <v-icon small class="mr-2" @click="editaUnidade(props.item)">edit</v-icon>
            <v-icon small @click="deletaUnidade(props.item)">delete</v-icon>
          </td>
        </tr>
      </template>
    </v-data-table>
  </div>
</template>

<script>
  import Axios from 'axios'

  export default {
    data() {
       return {
        totalCount: 0,
        unidades: [],
        loading: true,
        pagination: {},
        headers: [
          { text: 'Sigla', sortable: false, align: 'left', value: 'sigla' },
          { text: 'Nome', sortable: false, align: 'left', value: 'nome' },
          { text: 'Ações', sortable: false, align: 'center' },
        ]
      }
    },

    watch: {
      pagination: {
        handler() {
          this.getUnidades()
        },
        deep: true
      }
    },

    methods: {
      getUnidades () {
        this.loading = true

        Axios.get("/unidade?page=" + this.pagination.page + "&size=" + this.pagination.rowsPerPage).then(data => {
          this.unidades = data.data.data.items
          this.totalCount = data.data.data.count
          this.loading = false
        });
      },
  
      editaUnidade(unidade) {
        console.log(unidade);
      },

      deletaUnidade(unidade) {
        console.log(unidade);
      }
    }
  }
</script>