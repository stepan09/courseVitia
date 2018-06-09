<template>
  <div class="container">
    <div class="row">
      <div class="col-md-8">
        <input type="text" v-model="search" class="form-control" placeholder="Пошук">
      </div>
      <div class="col-md-4">
        <button type="button" @click="showModal = true" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter">
          Додати
        </button>
      </div>
    </div>
    <br>

    <b-card  v-for="sportKind in filteredList" :key="sportKind.id" v-bind:data="sportKind" :title="sportKind.name"
             img-src="../static/img/sport-kind.jpg"
             img-alt="Image"
             img-top
             tag="article"
             style="max-width: 20rem;"
             class="mb-2">
      <p class="card-text">
      </p>
      <b-button variant="success" @click="showUpdateModals(sportKind)">Змінити</b-button>
      <b-button @click="deleteSportKind(sportKind.id)" variant="danger">Видалити</b-button>
    </b-card>

    <!-- Modal -->
    <div v-if="showModal">
      <transition name="modal">
        <div class="modal-mask">
          <div class="modal-wrapper">
            <div class="modal-dialog sss">
              <div class="modal-content">
                <div class="modal-header">
                  Додати
                  <button type="button" class="close" @click="showModal=false">
                    <span aria-hidden="true">&times;</span>
                  </button>

                </div>
                <div class="modal-body">

                  <div class="form-group">
                    <label>Назва</label>
                    <input v-model="formAdd.name" type="text" class="form-control" placeholder="Введіть назву">
                  </div>

                  <button class="btn btn-warning" @click="showModal=false">Скасувати</button>
                  <button v-if="formAdd.name !== ''" class="btn btn-success" @click="addSportKind">Зберегти</button>
                  <button v-else class="btn btn-success" disabled>Зберегти</button>

                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>

<div v-if="showUpdateModal">
      <transition name="modal">
        <div class="modal-mask">
          <div class="modal-wrapper">
            <div class="modal-dialog sss">
              <div class="modal-content">
                <div class="modal-header">
                  Додати
                  <button type="button" class="close" @click="showUpdateModal=false">
                    <span aria-hidden="true">&times;</span>
                  </button>

                </div>
                <div class="modal-body">

                  <div class="form-group">
                    <label>Назва</label>
                    <input v-model="formAdd.name" type="text" class="form-control" placeholder="Введіть назву">
                  </div>

                  <button class="btn btn-warning" @click="showUpdateModal=false">Скасувати</button>
                  <button v-if="formAdd.name !== ''" class="btn btn-success" @click="addSportKind">Зберегти</button>
                  <button v-else class="btn btn-success" disabled>Зберегти</button>

                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>


  </div>
</template>

<script>
  import axios from 'axios'
    export default {
        name: "SportKind",
      data: () => ({
        search: '',
        sportKinds: [],
        formAdd: {},
        showModal: false,
        showUpdateModal: false,
  }),
      created() {
        this.fetchSportKinds();
      },
      methods: {
        fetchSportKinds() {
          axios.get('/api/sport-kinds').then((response) => {
            this.sportKinds = response.data;
          });
        },
        deleteSportKind(id) {
          axios.delete('/api/sport-kinds/' + id).then(() => {
            this.fetchSportKinds();
          });
        },
        addSportKind() {
          this.showModal = false;
          axios.post('/api/sport-kinds/', this.formAdd).then(() => {
            this.fetchSportKinds();
          });
          this.formAdd = {};
        },
        updateSportKind(sportKind) {
          this.showUpdateModal = false;
          console.log(this.formAdd);
          axios.put('/api/sport-kinds/' + sportKind.id, sportKind).then(() => {
            this.fetchSportKinds();
          });
          this.formAdd = {};
        },
        showUpdateModals(sportKind) {
          this.showUpdateModal = true;
          this.formAdd = sportKind;
        },
      },
      computed: {
        filteredList() {
          return this.sportKinds.filter(sportKind => {
            return sportKind.name.toLowerCase().includes(this.search.toLowerCase());
          })
        }
      }
    }
</script>

<style scoped>
  .container {
    margin-top: 20px;
  }
  .card {
    display: inline-block;
    margin: 8px;
  }

  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
    overflow-y: scroll;
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }
</style>
