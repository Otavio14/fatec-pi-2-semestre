import swal from "sweetalert2";

export const Swal = swal.mixin({
  background: "#f8f9ff",
  confirmButtonColor: "#2B38D1",
  color: "#0c2d57",
  cancelButtonColor: "#DD3842",
});

export const Toast = swal.mixin({
  toast: true,
  position: "top-end",
  background: "#f8f9ff",
  confirmButtonColor: "#2B38D1",
  color: "#0c2d57",
  showConfirmButton: false,
  timer: 1000,
  timerProgressBar: true,
});
