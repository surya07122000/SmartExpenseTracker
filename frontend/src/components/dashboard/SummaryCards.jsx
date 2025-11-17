const SummaryCard = ({ title, value, color }) => (
  <div className="col-md-3 mb-3">
    <div className={`card border-${color}`}>
      <div className={`card-body text-${color}`}>
        <h6 className="card-title">{title}</h6>
        <h4 className="card-text fw-bold">₹ {value?.toLocaleString()}</h4>
      </div>
    </div>
  </div>
);
 
export default SummaryCard;