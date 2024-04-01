const ReservationDetailsForm = () => {
  return (
    <div className="max-w-full">
      <label className="form-control w-full max-w-sm text-9xl">
        <div className="label">
          <span className="label-text">Input your reservation token</span>
        </div>
        <input
          type="text"
          placeholder="Type here"
          className="input input-bordered w-full max-w-xl"
        />
        <button
          onClick={() => handleConfirmButton()}
          className="btn btn-success text-green-200"
        >
          Confirm Details
        </button>
      </label>
    </div>
  );
};

export default ReservationDetailsForm;
